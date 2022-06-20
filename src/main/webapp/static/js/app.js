$(function(){
	var init = function (){
		initBuyBtn();
		$('#language').change(changeLanguage);
		$('#addToCart').click(addProductToCart);
		$('#addProductPopup .count').change(calculateCost);
		$('#loadMore').click(loadMoreProducts);
		$('#loadMoreMyOrders').click(loadMoreMyOrders);
		initSearchForm();
		$('#goSearch').click(goSearch);
		$('.remove-product').click(removeProductFromCart);
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
			success : function (data) {
				$('#currentShoppingCart .total-count').text(data.totalCount);
				$('#currentShoppingCart .total-cost').text(data.totalCost);
				$('#addProductPopup').modal('hide');
				$('#currentShoppingCart').removeClass('hidden');
				convertLoaderToButton(btn, 'btn-primary', addProductToCart);
			},
			error : function(xhr) {
				convertLoaderToButton(btn, 'btn-primary', addProductToCart);
				if (xhr.status === 400) {
					alert(xhr.responseJSON.message);
				} else {
					alert('Error');
				}
			}
		});
	};
	var calculateCost = function(){
		var priceStr = $('#addProductPopup .price').text();
		var price = parseFloat(priceStr.replace('$',' '));
		var count = parseInt($('#addProductPopup .count').val());
		var min = parseInt($('#addProductPopup .count').attr('min'));
		var max = parseInt($('#addProductPopup .count').attr('max'));
		if(count >= min && count <= max) {
			var cost = price * count;
			$('#addProductPopup .cost').text('$ '+cost);
		} else {
			$('#addProductPopup .count').val(1);
			$('#addProductPopup .cost').text(priceStr);
		}
	};

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

	var loadMoreProducts = function () {
		var btn = $('#loadMore');
		convertButtonToLoader(btn, 'btn-primary');
		var pageNumber = parseInt($('#productList').attr('data-page-number'));
		var myUrl = '/more' + location.pathname + '?page=' + (pageNumber + 1) + '&' + location.search.substring(1);

		$.ajax({
			url : myUrl,
			success : function (html) {
				$('#productList .row').append(html);
				pageNumber++;
				var pageCount = parseInt($('#productList').attr('data-page-count'));
				$('#productList').attr('data-page-number', pageNumber);
				if(pageNumber < pageCount) {
					convertLoaderToButton(btn, 'btn-primary', loadMoreProducts);
				} else {
					btn.remove();
				}
			},
			error : function(data) {
				convertLoaderToButton(btn, 'btn-primary', loadMoreProducts);
				alert('Error');
			}
		});
	};

	var loadMoreMyOrders = function (){
		var btn = $('#loadMoreMyOrders');
		convertButtonToLoader(btn, 'btn-primary');
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
					convertLoaderToButton(btn, 'btn-primary', loadMoreMyOrders);
				} else {
					btn.remove();
				}
			},
			error : function(xhr) {
				convertLoaderToButton(btn, 'btn-primary', loadMoreMyOrders);
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
	var confirm = function (msg, okFunction) {
		if(window.confirm(msg)) {
			okFunction();
		}
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
			var productCount = parseInt($(value).find('.productCount').text());
			var price = parseFloat($(value).find('.price').text().replace('$', ' '));
			var val = price * productCount;
			total = total + val;
		});
		$('#shoppingCart .total').text('$'+total);
	};
	var executeRemoveProduct = function (btn) {
		var idProduct = btn.attr('data-id-product');
		var productCount = btn.attr('data-count');
		convertButtonToLoader(btn, 'btn-danger');
		$.ajax({
			url : '/product/remove',
			method : 'POST',
			data : {
				idProduct : idProduct,
				productCount : productCount
			},
			success : function(data) {
				if (data.totalCount === 0) {
					$('#currentShoppingCart').addClass('hidden');
					window.location.href = '/products';
				} else {
					var prevCount = parseInt($('#product' + idProduct + ' .productCount').text());
					var remCount = parseInt(productCount);
					if (remCount >= prevCount) {
						$('#product' + idProduct).remove();
					} else {
						convertLoaderToButton(btn, 'btn-danger', removeProductFromCart);
						$('#product' + idProduct + ' .productCount').text(prevCount - remCount);
						if(prevCount - remCount === 1) {
							$('#product' + idProduct + ' a.remove-all').remove();
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