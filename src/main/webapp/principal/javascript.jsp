<!-- Required Jquery -->
    <script type="text/javascript" src="<%= request.getContextPath() %>/assets/js/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/assets/js/jquery-ui/jquery-ui.min.js "></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/assets/js/popper.js/popper.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/assets/js/bootstrap/js/bootstrap.min.js "></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/assets/pages/widget/excanvas.js "></script>
    <!-- waves js -->
    <script src="<%= request.getContextPath() %>/assets/pages/waves/js/waves.min.js"></script>
    <!-- jquery slimscroll js -->
    <script type="text/javascript" src="<%= request.getContextPath() %>/assets/js/jquery-slimscroll/jquery.slimscroll.js "></script>
    <!-- modernizr js -->
    <script type="text/javascript" src="<%= request.getContextPath() %>/assets/js/modernizr/modernizr.js "></script>
    <!-- slimscroll js -->
    <script type="text/javascript" src="<%= request.getContextPath() %>/assets/js/SmoothScroll.js"></script>
    <script src="<%= request.getContextPath() %>/assets/js/jquery.mCustomScrollbar.concat.min.js "></script>
    <!-- Chart js -->
    <script type="text/javascript" src="<%= request.getContextPath() %>/assets/js/chart.js/Chart.js"></script>
    <!-- amchart js -->
    <script src="https://www.amcharts.com/lib/3/amcharts.js"></script>
    <script src="<%= request.getContextPath() %>/assets/pages/widget/amchart/gauge.js"></script>
    <script src="<%= request.getContextPath() %>/assets/pages/widget/amchart/serial.js"></script>
    <script src="<%= request.getContextPath() %>/assets/pages/widget/amchart/light.js"></script>
    <script src="<%= request.getContextPath() %>/assets/pages/widget/amchart/pie.min.js"></script>
    <script src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
    <!-- menu js -->
    <script src="<%= request.getContextPath() %>/assets/js/pcoded.min.js"></script>
    <script src="<%= request.getContextPath() %>/assets/js/vertical-layout.min.js "></script>
    <!-- custom js -->
    <script type="text/javascript" src="<%= request.getContextPath() %>/assets/pages/dashboard/custom-dashboard.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/assets/js/script.js "></script>
    
    <!-- notification js -->
     <script type="text/javascript" src="<%= request.getContextPath() %>/assets/js/bootstrap-growl.min.js"></script>
     
     <script>
		'use strict';
		
		//Função global de notificação com mensagem personalizada
		function notify(from, align, icon, type, animIn, animOut, message = 'Mensagem padrão', title = '') {
		    $.growl({
		        icon: icon,
		        title: title,
		        message: message,
		        url: ''
		    }, {
		        element: 'body',
		        type: type,
		        allow_dismiss: true,
		        placement: {
		            from: from,
		            align: align
		        },
		        offset: {
		            x: 30,
		            y: 30
		        },
		        spacing: 10,
		        z_index: 999999,
		        delay: 5000,
		        timer: 2000,
		        url_target: '_blank',
		        mouse_over: false,
		        animate: {
		            enter: animIn,
		            exit: animOut
		        },
		        icon_type: 'class',
		        template: '<div data-growl="container" class="alert" role="alert">' +
		        '<button type="button" class="close" data-growl="dismiss">' +
		        '<span aria-hidden="true">&times;</span>' +
		        '<span class="sr-only">Close</span>' +
		        '</button>' +
		        '<span data-growl="icon"></span>' +
		        '<span data-growl="title"></span>' +
		        '<span data-growl="message"></span>' +
		        '<a href="#" data-growl="url"></a>' +
		        '</div>'
		    });
		}
		
		// Função facilitadora
		function triggerNotification(nFrom, nAlign, nIcons, nType, nAnimIn, nAnimOut, message, title) {
		    notify(nFrom, nAlign, nIcons, nType, nAnimIn, nAnimOut, message, title);
		}
		
		
		// Notificação de boas-vindas ao carregar a página
		$(window).on('load', function() {
		    // Você pode chamar aqui, se quiser:
		    // triggerNotification('bottom', 'right', 'fa fa-check', 'success', 'bounceInRight', 'bounceOutRight');
		});
		
		// Eventos prontos
		$('.notifications .btn').on('click', function(e){
		    e.preventDefault();
		    var nFrom = $(this).attr('data-from');
		    var nAlign = $(this).attr('data-align');
		    var nIcons = $(this).attr('data-icon');
		    var nType = $(this).attr('data-type');
		    var nAnimIn = $(this).attr('data-animation-in');
		    var nAnimOut = $(this).attr('data-animation-out');
		    var message = $(this).attr('data-message') || 'Mensagem padrão';
		    var title = $(this).attr('data-title') || '';
		
		    triggerNotification(nFrom, nAlign, nIcons, nType, nAnimIn, nAnimOut, message, title);
		})
		</script>

     