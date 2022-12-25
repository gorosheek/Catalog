$('#date').datepicker({
    todayBtn: "linked"
});

$('#addForm').submit(event => {
    const technicName = $('#technicName').val();
    const date = $('#date').val();
    const serviceName = $('#serviceName').val();

    const card = {
        technicName: technicName,
        date: date,
        serviceName: serviceName
    };

    $.ajax({
    	url: '/warrantyCard',
    	method: 'post',
    	data: card,
    	async: false,
    	error: () => {
    	    alert('Ошибка');
    	    event.preventDefault();
    	}
    });
});
