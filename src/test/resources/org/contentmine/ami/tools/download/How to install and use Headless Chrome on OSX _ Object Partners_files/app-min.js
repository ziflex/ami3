var menuToggle=function(a){function b(a,b,c){a.removeClass("disabled"),a.addClass("pushContent"),b.addClass("pushContent"),c.addClass("pushContent")}function c(a,b,c){a.addClass("disabled"),a.removeClass("pushContent"),b.removeClass("pushContent"),c.removeClass("pushContent")}var d={};return d.init=function(){var d=a("#navSlideout"),e=a("#page"),f=a("#header");a("#toggle").on("click",function(){d.hasClass("disabled")?b(d,e,f):c(d,e,f)}),a(window).on("resize",function(){a(window).width()>=767&&!d.hasClass("disabled")&&c(d,e,f)}),e.on("click",function(){a(this).hasClass("pushContent")&&c(d,e,f)}),d.find(".menu-item-has-children > a").append('<div class="subItem"><svg version="1.1" class="navPlus" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 36.8 36.8" enable-background="new 0 0 36.8 36.8" xml:space="preserve"><g><rect x="17.5" y="0" fill="#B82E2C" width="1.9" height="36.8"/><rect x="0" y="17.5" fill="#B82E2C" width="36.8" height="1.9"/></g></svg></div>'),a(".subItem").on("click",function(b){b.preventDefault();var c=a(this),d=a(this).parent().siblings(".sub-menu");d.is(":visible")?(d.slideUp(),c.removeClass("active")):(d.slideDown(),c.addClass("active"))})},d}(jQuery),circleRotate=function(a){function b(){var a=j.width(),b=a;j.height(b)}function c(){g(),l=2,k.attr("class","timer"),j.addClass("one"),a(".timer-1-path").attr("class",function(a,b){return b+" active"}),m=setInterval(e,3500)}function d(){clearInterval(m),l=1,f(),0==n&&j.addClass("one")}function e(){switch(l){case 1:a(".timer").removeClass("reset"),f(),j.addClass("one"),a(".timer-1-path").attr("class",function(a,b){return b+" active"});break;case 2:f(),j.addClass("two"),a(".timer-2-path").attr("class",function(a,b){return b+" active"});break;case 3:f(),j.addClass("three"),a(".timer-3-path").attr("class",function(a,b){return b+" active"});break;case 4:f(),j.addClass("four"),a(".timer-4-path").attr("class",function(a,b){return b+" active"});break;case 5:f(),j.addClass("reset"),k.addClass("reset"),g()}return 5==l?void(l=1):void l++}function f(){j.removeClass("one two three four reset")}function g(){a(".timer-path").attr("class",function(a,b){return b.replace("active","")})}function h(){j.addClass("owl-carousel rotation"),a(".owl-carousel.rotation").owlCarousel({loop:!0,items:1,margin:10,dots:!0,autoHeight:!0,responsive:{0:{items:1,nav:!1},768:{items:1,nav:!1},1e3:{items:1,nav:!1}},autoplay:!0})}function i(){a(".owl-carousel.rotation").trigger("destroy.owl.carousel"),a(".owl-carousel.rotation").find(".owl-stage-outer").children().unwrap(),j.removeClass("owl-carousel owl-loaded rotation")}var j=a(".clockSlider"),k=a(".timer"),l=2,m=null,n=!1,o={};return o.init=function(){var e=!1;a(window).on("load resize",function(){j.length&&a(window).width()>=768?(b(),0==e&&(c(),i(),e=!0)):(e=!1,d(),h())}),a(".timerSection").on("click",function(){var b=a(this).attr("data-section");n=!0,d(),g(),j.addClass(b),k.attr("class","timer"),k.addClass(b)})},o}(jQuery),owlCarousel=function(a){var b={};return b.init=function(){var b=a(".owl-carousel.heroSwap"),c=a(".owl-carousel.cloud");c.owlCarousel({loop:!0,margin:10,responsiveClass:!0,responsive:{0:{items:2,nav:!0},768:{items:3,nav:!1},1e3:{items:5,nav:!0,loop:!1}}}),b.owlCarousel({loop:!0,items:1,dots:!1,autoHeight:!0,autoplay:!0,autoplayTimeout:setting_var.taglineFlipSpeed,mouseDrag:!1,touchDrag:!1,pullDrag:!1,animateIn:"flipInX"})},b}(jQuery),workCarousel=function(a){var b={};return b.init=function(){var b=a(".owl-carousel.study");b.length&&a(".owl-carousel").each(function(){a(this).find(".item").length>1&&b.owlCarousel({loop:!0,items:1,margin:10,dots:!0,autoHeight:!0})})},b}(jQuery),selectOption=function(a){var b={};return b.init=function(){a("#page-changer").length&&(a("#submitCat").hide(),""!=a("#page-changer select option:selected").val()&&a("#page-changer select option:first-child").prop("selected",!0),a("#page-changer select").change(function(){var b=a("#page-changer select option:selected").val();window.location=window.location.origin+"/tag/"+b})),a("#searchform")&&a("#searchsubmit").hide()},b}(jQuery),tabs=function(a){var b={};return b.init=function(){if(a(".tabs").length){var b=a(".tab:first-child"),c=a(".tabSection:first-child");b.addClass("active"),c.addClass("active"),a(".tab").on("click",function(){var b=a(this).attr("id");a(this).hasClass("active")||(a(".tab").removeClass("active"),a(".tabSection").removeClass("active"),a(this).addClass("active"),a(".tabSection."+b).addClass("active"))})}},b}(jQuery),videofix=function(a){var b={};return b.init=function(){a("iframe").each(function(){var b=a(this).attr("src");a(this).attr("src").indexOf("?")>0?a(this).attr({src:b+"&wmode=transparent",wmode:"Opaque"}):a(this).attr({src:b+"?wmode=transparent",wmode:"Opaque"})})},b}(jQuery),flexsliderHero=function(a){var b={};return b.init=function(){a(".flexslider").flexslider({animation:"fade",controlNav:!1,directionNav:!1,slideshowSpeed:setting_var.slideshowSpeed})},b}(jQuery),youtubeVideoActivate=function(a){var b={};return b.init=function(){a(".js-youtube-play").on("click",function(b){var c=a(".featuredVideo-content iframe"),d=a(".featuredVideo-overlay"),e=a(".playContent");c.css({visibility:"visible",opacity:1}),c[0].src+="&autoplay=1",e.addClass("playContent-hide"),d.css("opacity",0),b.preventDefault()})},b}(jQuery),CONTROL=function(a){a(document).ready(function(){menuToggle.init(),circleRotate.init(),selectOption.init(),tabs.init(),videofix.init(),youtubeVideoActivate.init()}),a(window).on("load",function(){flexsliderHero.init(),owlCarousel.init(),workCarousel.init()})}(jQuery);