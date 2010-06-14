(function($) {
$.fn.medcafeTouch = function(options) {
    // Default thresholds & swipe functions
    var defaults = {
        threshold: {
            x: 30,
            y: 10
        },
        swipeLeft: function() { alert('swiped left') },
        swipeRight: function() { alert('swiped right') },
        preventDefaultEvents: true
    };

    var options = $.extend(defaults, options);

    if (!this) return false;

    return this.each(function() {

        var me = $(this)

        // Private variables for each element
        var originalCoord = { x: 0, y: 0 }
        var finalCoord = { x: 0, y: 0 }

        // Screen touched, store the original coordinate
        function touchStart(event) {
           // console.log('Starting swipe gesture...')
            originalCoord.x = event.targetTouches[0].pageX
            originalCoord.y = event.targetTouches[0].pageY
            console.log('Touch start..position x: ' + finalCoord.x +' y : ' + finalCoord.y);
        }

        // Store coordinates as finger is swiping
        function touchMove(event) {
            if (defaults.preventDefaultEvents)
                event.preventDefault();
            finalCoord.x = event.targetTouches[0].pageX // Updated X,Y coordinates
            finalCoord.y = event.targetTouches[0].pageY
            //console.log('Touch move..position x: ' + finalCoord.x +' y : ' + finalCoord.y);
            //console.log('medcafe.jswipe.js touchMove: Touch move..start');  
            //console.log('Touch move..position x: ' + finalCoord.x +' y : ' + finalCoord.y);
            $(this).css( { position: "absolute",  "z-index" : "100", "left": finalCoord.x + "px", "top": finalCoord.y + "px" } );
	  	
        }

        // Done Swiping
        // Swipe should only be on X axis, ignore if swipe on Y axis
        // Calculate if the swipe was left or right
        function touchEnd(event) {
            //console.log('Ending swipe gesture...')
            var changeY = originalCoord.y - finalCoord.y
            if(changeY < defaults.threshold.y && changeY > (defaults.threshold.y*-1)) {
                changeX = originalCoord.x - finalCoord.x

                if(changeX > defaults.threshold.x) {
                    defaults.swipeLeft()
                }
                if(changeX < (defaults.threshold.x*-1)) {
                    defaults.swipeRight()
                }
            }
            console.log('Ending swipe gesture..position x: ' + finalCoord.x +' y : ' + finalCoord.y);
        }

        // Swipe was canceled
        function touchCancel(event) { 
            console.log('Canceling swipe gesture...')
        }

        // Add gestures to all swipable areas
        this.addEventListener("touchstart", touchStart, false);
        this.addEventListener("touchmove", touchMove, false);
        this.addEventListener("touchend", touchEnd, false);
        this.addEventListener("touchcancel", touchCancel, false);

    });
};
})(jQuery);

