let userId, userList, chatArea, messageArea, ws, chatId
const messages = new Map()

function init() {
    elementInit();
    wsInit()
    generalInit();
    userOnlineInit();
}

function elementInit() {
    userId = getCookieValue('userId')
    userList = document.getElementById('user-list')
    chatArea = document.getElementById('chat-area')
    messageArea = document.getElementById('message-area')
    chatId = 'GENERAL'
}

function wsInit() {
    ws = new WebSocket('ws://' + window.location.host + '/connect/')

    ws.onmessage = message => {
        let messageData = JSON.parse(message.data)

        if (messageData.messageType === 'TEXT') {
            addMessage(messageData)
        } else if (messageData.messageType === 'USER_ONLINE') {
            addUserInUserList(messageData)
        } else if (messageData.messageType === 'USER_OFFLINE') {
            removeUserInUserList(messageData)
        }
    }

    ws.onclose = () => {
        logout()
    }
}

function generalInit() {
    messages.set('GENERAL', [])

    fetch('/chat/GENERAL')
        .then(response => response.json())
        .then(response => {
                response.forEach(message => {
                        messages.get('GENERAL').push(message)
                        chatArea.value += message.from.split(':')[1] + ':' + message.text + '\n'
                    }
                )
            }
        );
}

function userOnlineInit() {
    fetch('/user/online/')
        .then(response => response.json())
        .then(response => {
                response.forEach(user => {
                        if (userId !== user.userId) {
                            userList.add(new Option(user.name, user.userId))

                            addMessageForChatId(
                                getChatId(user.userId)
                            )
                        }
                    }
                )
            }
        );
}

function addMessageForChatId(chatId) {
    messages.set(chatId, [])

    fetch('/chat/' + chatId)
        .then(response => response.json())
        .then(response => {
                response.forEach(message => {
                        messages.get(chatId).push(message)
                    }
                )
            }
        );
}

function addMessage(message) {
    if (chatId === message.chatId) {
        chatArea.value += message.from.split(':')[1] + ': ' + message.text + '\n'
    }

    messages.get(message.to === 'GENERAL' ?
        'GENERAL' :
        getChatId(message.from)
    ).push(message)
}

function addUserInUserList(messageData) {
    if (messageData.from !== userId) {
        userList.add(new Option(messageData.from.split(':')[1], messageData.from))

        addMessageForChatId(
            getChatId(messageData.from)
        )
    }
}

function getChatId(fromId) {
    return fromId === 'GENERAL' ? fromId : [userId, fromId].sort().join('|')
}

function removeUserInUserList(messageData) {
    for (let i = 0; i < userList.length; i++) {
        if (userList.options[i].value === messageData.from)
            userList.remove(i);
    }
}

function sendMessage() {
    let message = createMessage(userId, userList.value, messageArea.value, chatId, 'TEXT')

    messages.get(chatId).push(message)

    if (userList.value !== 'GENERAL') {
        chatArea.value += userId.split(':')[1] + ': ' + messageArea.value + '\n'
    }

    ws.send(JSON.stringify(message));

    messageArea.value = ''
}

function logout() {
    window.location.href = window.location.origin + '/logout';
}

function selectUser() {
    chatId = getChatId(userList.value)
    chatArea.value = ''
    messages.get(chatId).forEach(message => {
        chatArea.value += message.from.split(':')[1] + ':' + message.text + '\n'
    })
}

function getCookieValue(name) {
    return document.cookie.match('(^|;)\\s*' + name + '\\s*=\\s*([^;]+)')?.pop() || ''
}

function createMessage(from, to, text, chatId, messageType, time = Date.now()) {
    return {from, to, text, chatId, messageType, time}
}

function downloadMessage() {
    let blob = new Blob(
        [JSON.stringify(messages.get(chatId), null, 2)],
        {type: "application/json;charset=" + document.characterSet}
    );
    let link = document.createElement("a");
    link.setAttribute("href", URL.createObjectURL(blob));
    link.setAttribute("download", Date.now().toString());
    link.click();
}