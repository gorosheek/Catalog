$('#logout').click(function() {
    $.ajax({
        url: '/user/logout',
        type: 'GET',
        success: function() {
            location.reload();
        }
    });
});

let isEnded = false;

function update() {
$.get(`/warrantyCard/all?isEnded=${isEnded}`, function(data){
	const goods = $('#technics');
	goods.empty();
	for (const good of data) {
	    const gBlock = $('<div class="card"></div>');
	    const nameBlock = $(`<div class="title">${good.technicName}</div>`);
	    const imageBlock = $(`<div class="image">${good.serviceName}</div>`);
	    const costBlock = $(`<span class="cost">${good.endDate}</span>`);
	    const deleteBtn = $('<button>Удалить</button>');
	    deleteBtn.click(event => {
	            $.ajax({
                            	url: `/warrantyCard/${good.id}`,
                            	method: 'delete',
                            	async: false
                            });
                location.reload();
	    });
	    const updateBtn = $(`<a href="${'/updateForm/' + good.id}">Изменить</a>`);
	    gBlock.append(nameBlock);
	    gBlock.append(imageBlock);
	    gBlock.append(costBlock);
	    gBlock.append(deleteBtn);
	    gBlock.append(updateBtn);
	    goods.append(gBlock);
	}
});
}

$('#isEnded').click(function() {
    isEnded = !isEnded;
    update();
});

update();