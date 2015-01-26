$(document).ready(function(){
    var options = {
        nextButton: true,
        prevButton: true,
        pagination: false,
        animateStartingFrameIn: true,
        autoPlay: false,
        autoPlayDelay: 3000,
        preloader: false
    };
    
    var mySequence = $("#sequence").sequence(options).data("sequence");
});