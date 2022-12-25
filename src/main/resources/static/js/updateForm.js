$('#date').datepicker({
    todayBtn: "linked"
});

const cardId = $('#cardId').text();

$('#addForm').submit(event => {
    const technicName = $('#technicName').val();
    const date = $('#date').val();
    const serviceName = $('#serviceName').val();

    const card = {
        technicName: technicName,
        date: date,
        serviceName: serviceName,
        cardId: cardId
    };

    $.ajax({
    	url: '/warrantyCard',
    	method: 'put',
    	data: card,
    	async: false,
    	error: () => {
    	    alert('Ошибка');
    	    event.preventDefault();
    	}
    });
});

$.ajax({
    url: `/warrantyCard/${cardId}`,
    method: 'get',
    async: false,
    success: data => {
        $('#technicName').val(data.technicName);
        $('#date').val(data.endDate);
        $('#serviceName').val(data.serviceName);
    }
});