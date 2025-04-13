<script>
	$('body')
			.append(
					'<div class="footerwhatsapp">'
							+ '<a target="_blank" href="https://api.whatsapp.com/send?phone=5591983839680&text=Ol%C3%A1.">'
							+ '<img src="https://upload.wikimedia.org/wikipedia/commons/6/6b/WhatsApp.svg"'
												+ ' style="height: 75px; width: 75px";'
												+ 'title="WhatsApp - Plantão de Vendas">;'
							+ '</a>' + '</div>');

	var $window = $(window);
	var nav = $('.footerwhatsapp');
	$window.scroll(function() {
		if ($window.scrollTop() >= 200) {
			nav.fadeIn();
		} else {
			nav.fadeOut();
		}
	});
</script>