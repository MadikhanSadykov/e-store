;$(function(){
	var init = function (){
		initBuyBtn();
		$('#language').change(changeLanguage);
		$('#addToCart').click(addProductToCart);
		$('#addProductPopup .productCount').change(calculateCost);
		$('#loadMore').click(loadMoreProducts);
		$('#loadMoreAdminProducts').click(loadMoreAdminProducts);
		$('#loadMoreAllOrders').click(loadMoreAllOrders);
		$('#loadMoreMyOrders').click(loadMoreMyOrders);
		initSearchForm();
		$('#goSearch').click(goSearch);
		$('.remove-product').click(removeProductFromCart);
		$('.post-request').click(function(){
			postRequest($(this).attr('data-url'));
		});

		$(document).on("click", ".delete-product", deleteProduct);
		$(document).on("click", ".complete-order", completeOrder);
		$(document).on("click", ".cancel-order", cancelOrder);
		$(document).on("click", ".as-admin", markAsAdmin);
		$(document).on("click", ".as-user", markAsNotAdmin);
		$(document).on("click", ".delete-user", deleteUser);
	};

	var deleteUser = function () {
	var btn = $(this);
	confirm('Are you sure?', function(){
		executeDeleteUser(btn);
	});
	}

	var executeDeleteUser = function (btn) {
		var userID = btn.attr('data-id-user');
		convertButtonToLoader(btn, 'btn-danger');
		$.ajax({
			url: '/user/delete',
			method: 'POST',
			data : {
				userID : userID
			},
			success : function (data) {
				convertLoaderToButton(btn, 'btn-danger', deleteUser);
				location.reload();
			},
			error : function (data) {
				convertLoaderToButton(btn, 'btn-danger', deleteUser);
				alert('User with id ' + userID + ' has order/s');
			}
		});
	}

	var markAsNotAdmin = function () {
	var btn = $(this);
	confirm('Are you sure?', function(){
		executeMarkAsNotAdmin(btn);
	});
	};

	var executeMarkAsNotAdmin = function (btn) {
		var userID = btn.attr('data-id-user');
		convertButtonToLoader(btn, 'btn-warning');
		$.ajax({
			url: '/user/notAdmin',
			method: 'POST',
			data : {
				userID : userID
			},
			success : function (data) {
				convertLoaderToButton(btn, 'btn-success', markAsNotAdmin);
				location.reload();
			},
			error : function (data) {
				convertLoaderToButton(btn, 'btn-success', markAsNotAdmin);
				alert('Cannot mark user with id ' + userID + ' as admin');
			}
		});
	};

	var markAsAdmin = function () {
	var btn = $(this);
	confirm('Are you sure?', function(){
		executeMarkAsAdmin(btn);
	});
	};

	var executeMarkAsAdmin = function (btn) {
		var userID = btn.attr('data-id-user');
		convertButtonToLoader(btn, 'btn-warning');
		$.ajax({
			url: '/user/isAdmin',
			method: 'POST',
			data : {
				userID : userID
			},
			success : function (data) {
				convertLoaderToButton(btn, 'btn-warning', markAsAdmin);
				location.reload();
			},
			error : function (data) {
				convertLoaderToButton(btn, 'btn-warning', markAsAdmin);
				alert('Cannot mark user with id ' + userID + ' as admin');
			}
		});
	};


	var cancelOrder = function () {
	var btn = $(this);
	confirm('Are you sure?', function(){
		executeCancelOrder(btn);
	});
	};

	var executeCancelOrder = function (btn) {
		var orderID = btn.attr('data-id-order');
		convertButtonToLoader(btn, 'btn-danger');
		$.ajax({
			url: '/order/cancel',
			method: 'POST',
			data : {
				orderID : orderID
			},
			success : function (data) {
				convertLoaderToButton(btn, 'btn-success', cancelOrder);
				$('#order' + orderID).remove();
			},
			error : function (data) {
				convertLoaderToButton(btn, 'btn-danger', cancelOrder);
				alert('Order with id ' + orderID + ' cannot change status');
			}
		});
	}

	var completeOrder = function () {
	var btn = $(this);
	confirm('Are you sure?', function(){
		executeCompleteOrder(btn);
	});
	}

	var executeCompleteOrder = function (btn) {
		var orderID = btn.attr('data-id-order');
		convertButtonToLoader(btn, 'btn-success');
		$.ajax({
			url: '/order/complete',
			method: 'POST',
			data : {
				orderID : orderID
			},
			success : function (data) {
				convertLoaderToButton(btn, 'btn-success', completeOrder);
				$('#order' + orderID).remove();
			},
			error : function (data) {
				convertLoaderToButton(btn, 'btn-success');
				alert('Order with id ' + orderID + ' cannot change status', completeOrder);
			}
		});
	}

	var loadMoreAllOrders = function (){
	var btn = $('#loadMoreAllOrders');
	convertButtonToLoader(btn, 'btn-primary');
	var pageNumber = parseInt($('#allOrders').attr('data-page-number'));
	var myUrl = '/more' + location.pathname + '?page=' + (pageNumber + 1) + '&' + location.search.substring(1);
		$.ajax({
			url : myUrl,
			success : function(html) {
				$('#allOrders tbody').append(html);
				pageNumber++;
				var pageCount = parseInt($('#allOrders').attr('data-page-count'));
				$('#allOrders').attr('data-page-number', pageNumber);
				if (pageNumber < pageCount) {
					convertLoaderToButton(btn, 'btn-primary', loadMoreAllOrders);
				} else {
					btn.remove();
				}
			},
			error : function(xhr) {
				convertLoaderToButton(btn, 'btn-primary', loadMoreAllOrders);
				if (xhr.status == 401) {
					window.location.href = '/sign-in';
				} else {
					alert('You are not owner!');
				}
			}
		});
	};


	var deleteProduct = function () {
	var btn = $(this);
	confirm('Are you sure?', function(){
		executeDeleteProduct(btn);
	});
	}

	var changeLanguage = function () {
	var selected = $('#language').find(":selected").val();
	$.get('/changeLang?lang=' + selected);
		$.ajax({
			type: "GET",
			url: location.pathname,
			success: function () {
				location.reload();
			}
		});
	}

	var executeDeleteProduct = function(btn) {
	var productID = btn.attr('data-id-product');
	convertButtonToLoader(btn, 'btn-danger');
		$.ajax({
			url: '/product/delete',
			method: 'POST',
			data : {
				productID : productID
			},
			success : function (data) {
				$('#product' + productID).remove();
			},
			error : function (data) {
				convertLoaderToButton(btn, 'btn-danger');
				alert('Product with id ' + productID + ' in order/s');
			}
		});
	}

	var loadMoreAdminProducts = function (){
	var btn = $('#loadMoreAdminProducts');
	convertButtonToLoader(btn, 'btn-primary');
	var pageNumber = parseInt($('#allProducts').attr('data-page-number'));
	var myUrl = '/more' + location.pathname + '?page=' + (pageNumber + 1) + '&' + location.search.substring(1);
		$.ajax({
			url : myUrl,
			success : function(html) {
				$('#allProducts tbody').append(html);
				pageNumber++;
				var pageCount = parseInt($('#allProducts').attr('data-page-count'));
				$('#allProducts').attr('data-page-number', pageNumber);
				if (pageNumber < pageCount) {
					convertLoaderToButton(btn, 'btn-primary', loadMoreAdminProducts);
				} else {
					btn.remove();
				}
			},
			error : function(xhr) {
				convertLoaderToButton(btn, 'btn-primary', loadMoreAdminProducts);
				if (xhr.status == 401) {
					window.location.href = '/sign-in';
				} else {
					alert('You are not admin!');
				}
			}
		});
	}

	var convertButtonToLoader = function (btn, btnClass) {
		btn.removeClass(btnClass);
		btn.removeClass('btn');
		btn.addClass('load-indicator');
		var text = btn.text();
		btn.text('');
		btn.attr('data-btn-text', text);
		btn.off('click');
	};
	var convertLoaderToButton = function (btn, btnClass, actionClick) {
		btn.removeClass('load-indicator');
		btn.addClass('btn');
		btn.addClass(btnClass);
		btn.text(btn.attr('data-btn-text'));
		btn.removeAttr('data-btn-text');
		btn.click(actionClick);
	};
	var postRequest = function(url){
		var form = '<form id="postRequestForm" action="'+url+'" method="post"></form>';
		$('body').append(form);
		$('#postRequestForm').submit();
	};
	var confirm = function (msg, okFunction) {
		if(window.confirm(msg)) {
			okFunction();
		}
	};
	var alert = function (msg) {
		window.alert(msg);
	};
	var showAddProductPopup = function (){
		var idProduct = $(this).attr('data-id-product');
		var product = $('#product'+idProduct);
		$('#addProductPopup').attr('data-id-product', idProduct);
		$('#addProductPopup .product-image').attr('src', product.find('.thumbnail img').attr('src'));
		$('#addProductPopup .name').text(product.find('.name').text());
		var price = product.find('.price').text();
		$('#addProductPopup .price').text(price);
		$('#addProductPopup .category').text(product.find('.category').text());
		$('#addProductPopup .producer').text(product.find('.producer').text());
		$('#addProductPopup .count').val(1);
		$('#addProductPopup .cost').text(price);
		$('#addToCart').removeClass('hidden');
		$('#addToCartIndicator').addClass('hidden');
		$('#addProductPopup').modal({
			show:true
		});
	};

	var initBuyBtn = function(){
		$('.buy-btn').click(showAddProductPopup);
	};
	var addProductToCart = function (){
		var idProduct = $('#addProductPopup').attr('data-id-product');
		var productCount = $('#addProductPopup .productCount').val();
		var btn = $('#addToCart');
		convertButtonToLoader(btn, 'btn-primary');
		$.ajax({
			url : '/product/add',
			method : 'POST',
			data : {
				idProduct : idProduct,
				productCount : productCount
			},
			success : function(data) {
				$('#currentShoppingCart .total-count').text(data.totalCount);
				$('#currentShoppingCart .total-cost').text(data.totalCost);
				$('#currentShoppingCart').removeClass('hidden');
				convertLoaderToButton(btn, 'btn-primary', addProductToCart);
				$('#addProductPopup').modal('hide');
			},
			error : function(xhr) {
				convertLoaderToButton(btn, 'btn-primary', addProductToCart);
				if (xhr.status == 400) {
					alert(xhr.responseJSON.message);
				} else {
					alert(xhr.status + xhr.message);
				}
			}
		});
	};
	var calculateCost = function(){
		var priceStr = $('#addProductPopup .price').text();
		var price = parseFloat(priceStr.replace('$',' '));
		var count = parseInt($('#addProductPopup .productCount').val());
		var min = parseInt($('#addProductPopup .productCount').attr('min'));
		var max = parseInt($('#addProductPopup .productCount').attr('max'));
		if(count >= min && count <= max) {
			var cost = price * count;
			$('#addProductPopup .cost').text('$ '+cost);
		} else {
			$('#addProductPopup .productCount').val(1);
			$('#addProductPopup .cost').text(priceStr);
		}
	};


	var loadMoreProducts = function (){
		var btn = $('#loadMore');
		convertButtonToLoader(btn, 'btn-primary');
		var pageNumber = parseInt($('#productList').attr('data-page-number'));
		var url = '/more' + location.pathname + '?page=' + (pageNumber + 1) + '&' + location.search.substring(1);
		$.ajax({
			url : url,
			success : function(html) {
				$('#productList .row').append(html);
				pageNumber++;
				var pageCount = parseInt($('#productList').attr('data-page-count'));
				$('#productList').attr('data-page-number', pageNumber);
				if(pageNumber < pageCount) {
					convertLoaderToButton(btn, 'btn-primary', loadMoreProducts);
				} else {
					btn.remove();
				}
				initBuyBtn();
			},
			error : function(data) {
				convertLoaderToButton(btn, 'btn-primary', loadMoreProducts);
				alert('Error');
			}
		});
	};

	var loadMoreMyOrders = function (){
		var btn = $('#loadMoreMyOrders');
		convertButtonToLoader(btn, 'btn-success');
		var pageNumber = parseInt($('#myOrders').attr('data-page-number'));
		var url = '/more/my-orders?page=' + (pageNumber + 1);
		$.ajax({
			url : url,
			success : function(html) {
				$('#myOrders tbody').append(html);
				pageNumber++;
				var pageCount = parseInt($('#myOrders').attr('data-page-count'));
				$('#myOrders').attr('data-page-number', pageNumber);
				if (pageNumber < pageCount) {
					convertLoaderToButton(btn, 'btn-success', loadMoreMyOrders);
				} else {
					btn.remove();
				}
			},
			error : function(xhr) {
				convertLoaderToButton(btn, 'btn-success', loadMoreMyOrders);
				if (xhr.status == 401) {
					window.location.href = '/sign-in';
				} else {
					alert('Error');
				}
			}
		});
	};

	var initSearchForm = function (){
		$('#allCategories').click(function(){
			$('.categories .search-option').prop('checked', $(this).is(':checked'));
		});
		$('.categories .search-option').click(function(){
			$('#allCategories').prop('checked', false);
		});
		$('#allProducers').click(function(){
			$('.producers .search-option').prop('checked', $(this).is(':checked'));
		});
		$('.producers .search-option').click(function(){
			$('#allProducers').prop('checked', false);
		});
	};
	var goSearch = function(){
		var isAllSelected = function(selector) {
			var unchecked = 0;
			$(selector).each(function(index, value) {
				if(!$(value).is(':checked')) {
					unchecked ++;
				}
			});
			return unchecked === 0;
		};
		if(isAllSelected('.categories .search-option')) {
			$('.categories .search-option').prop('checked', false);
		}
		if(isAllSelected('.producers .search-option')) {
			$('.producers .search-option').prop('checked', false);
		}
		$('form.search').submit();
	};
	var removeProductFromCart = function (){
		var btn = $(this);
		confirm('Are you sure?', function(){
			executeRemoveProduct(btn);
		});
	};
	var refreshTotalCost = function () {
		var total = 0;
		$('#shoppingCart .item').each(function(index, value) {
			var count = parseInt($(value).find('.productCount').text());
			var price = parseFloat($(value).find('.price').text().replace('$', ' '));
			var val = price * count;
			total = total + val;
		});
		$('#shoppingCart .total').text('$'+total);
	};
	var executeRemoveProduct = function (btn) {
		var idProduct = btn.attr('data-id-product');
		var productCount = btn.attr('data-count');
		$.ajax({
			url : '/product/remove',
			method : 'POST',
			data : {
				idProduct : idProduct,
				productCount : productCount
			},
			success : function(data) {
				if (data.totalCount == 0) {
					window.location.href = '/products';
				} else {
					var prevCount = parseInt($('#product' + idProduct + ' .productCount').text());
					var remCount = parseInt(productCount);
					if (remCount >= prevCount) {
						$('#product' + idProduct).remove();
					} else {
						$('#product' + idProduct + ' .count').text(prevCount - remCount);
						if(prevCount - remCount <= 1) {
							$('#product' + idProduct + ' a.remove-product.all').remove();
						}
					}
					refreshTotalCost();
				}
			},
			error : function(data) {
				convertLoaderToButton(btn, 'btn-danger', removeProductFromCart);
				alert('Error');
			}
		});
	}

	init();
});