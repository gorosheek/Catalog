$(() => {
    $('form').submit((e) => {
        const login = $('#login').val();
        const pass = $('#pass').val();
        const email = $('#email').val();
        const user = {
            login: login,
            password: pass,
            email: email
        };

        $.ajax({
            url: '/user/reg',
            method: 'post',
            data: user,
            async: false,
            error: () => {
                alert('Ошибка');
                e.preventDefault();
            }
        });
    });
});