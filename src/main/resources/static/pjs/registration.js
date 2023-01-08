function registration() {
    fetch('user', {
        method: 'POST',
        headers: {
            'Accept': 'application/json, text/plain, */*',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            login: document.getElementById('login').value,
            password: document.getElementById('password').value,
            name: document.getElementById('name').value
        })
    })
        .then(response => response.json())
        .then(response => {
                if (response.id === null) {
                    alert("User with given login already exists")
                } else {
                    window.location.href = window.location.origin + '/login'
                }
            }
        );
}
