# test_task_for_bfg
Подключение к redis конфигурируется через application.properties

После старта сервис доступен по адресу localhost:8080

Сервис работает через WebSocket, Redis используется как брокер сообщений и хранилище данных.

Не аутентифицированным пользователем доступна возможность войти в чат и зарегистрироваться

При старте сервиса создаются 2 пользователя login: user password: user и login: test password: test

Аутентифицированные пользователи имеют возможность отправлять сообщения всем через GENERAL, либо личное сообщение выбрав пользователя из списка.

Если кто-то повторно будет заходить уже за аутентифицированного пользователя то, активного пользователя отключит, а тот кто входил пустит в чат.

Пример работы:
<p align="center">
<img  src="https://github.com/intTWONEh/test_task_for_bfg/blob/master/check.png" alt="check.png">
</p>

(тесты написать не успел)
