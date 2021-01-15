(function(){
  $(document).click(function() {
     var $item = $(".shopping-cart");
     if ($item.hasClass("active")) {
       $item.removeClass("active");
     }
   });
   
   $('.shopping-cart').each(function() {
     var delay = $(this).index() * 50 + 'ms';
     $(this).css({
         '-webkit-transition-delay': delay,
         '-moz-transition-delay': delay,
         '-o-transition-delay': delay,
         'transition-delay': delay
     });
   });
   $('#cart').click(function(e) {
     e.stopPropagation();
     $(".shopping-cart").toggleClass("active");
   });
   
   $('#addtocart').click(function(e) {
     e.stopPropagation();
     $(".shopping-cart").toggleClass("active");
   });


const hamburger = document.querySelector(".hamburger");
const navLinks = document.querySelector(".nav-links");
const links = document.querySelectorAll(".nav-links li");

hamburger.addEventListener('click', ()=>{
   //Animate Links
    navLinks.classList.toggle("open");
    links.forEach(link => {
        link.classList.toggle("fade");
    });

    //Hamburger Animation
    hamburger.classList.toggle("toggle");
});
 
 
   
 })();