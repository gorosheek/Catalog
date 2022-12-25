$(() => {
    $('form').submit((e) => {
        const login = $('#login').val();
        const pass = $('#pass').val();
        const user = {
            login: login,
            password: pass
        };

        $.ajax({
            url: '/user/login',
            method: 'get',
            data: user,
            async: false,
            error: () => {
                alert('Неверные данные');
                e.preventDefault();
            }
        });
    });
});