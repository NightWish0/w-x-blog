/** 
 * Designed by @Taras Shypka
 * https://dribbble.com/Bugsster
 * Coded by @Balaj Marius for @Designmodo
 * http://mariusbalaj.com | http://designmodo.com
 */
function effet(poster,shine,layer,e){
    var w = $(window).width(), //window width
        h = $(window).height(), //window height
        offsetX = 0.5 - e.pageX / w, //cursor position X
        offsetY = 0.5 - e.pageY / h, //cursor position Y
        dy = e.pageY - h / 2, //@h/2 = center of poster
        dx = e.pageX - w / 2, //@w/2 = center of poster
        theta = Math.atan2(dy, dx), //angle between cursor and center of poster in RAD
        angle = theta * 180 / Math.PI - 90, //convert rad in degrees
        offsetPoster = poster.data('offset'),
        transformPoster = 'translateY(' + -offsetX * offsetPoster + 'px) rotateX(' + (-offsetY * offsetPoster) + 'deg) rotateY(' + (offsetX * (offsetPoster * 2)) + 'deg)'; //poster transform
    //get angle between 0-360
    if (angle < 0) {
        angle = angle + 360;
    }
    //gradient angle and opacity
    shine.css('background', 'linear-gradient(' + angle + 'deg, rgba(255,255,255,' + e.pageY / h * .5 + ') 0%,rgba(255,255,255,0) 80%)');
    //poster transform
    poster.css('transform', transformPoster);
    //parallax foreach layer
    layer.each(function() {
        var $this = $(this),
            offsetLayer = $this.data('offset') || 0,
            transformLayer = 'translateX(' + offsetX * offsetLayer + 'px) translateY(' + offsetY * offsetLayer + 'px)';
        $this.css('transform', transformLayer);
    });
}
$('.user-1').on('mousemove', function(e) {
    var $poster = $('.user-1.poster'),
        $shine = $('.user-1.shine'),
        $layer = $('.user-1 div[class*="layer-"]');
    effet($poster,$shine,$layer,e);
});
$('.user-2').on('mousemove', function(e) {
    var $poster2 = $('.user-2.poster'),
        $shine2 = $('.user-2.shine'),
        $layer2 = $('.user-2 div[class*="layer-"]');
    effet($poster2,$shine2,$layer2,e);
});