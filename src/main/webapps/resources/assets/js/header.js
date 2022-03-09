
	const burger = document.querySelector(".burger");
	const nav = document.querySelector(".nav-links");
	const navlinks = document.querySelectorAll(".nav-links li");
	const navAnimation = () => {
		  navlinks.forEach((link, index) => {
		    if (link.style.animation) {
		      link.style.animation = "";
		    } else {
		      link.style.animation = `navLinkFade 0.5s ease forwards ${
		        index / 7 + 0.5
		      }s`;
		    }
		  });
		};
		const handleNav = () => {
		  nav.classList.toggle("nav-active");
		  navAnimation();
		  burger.classList.toggle("toggle");
		};
		const navSlide = () => {
		  burger.addEventListener("click", handleNav);
		};

		const setNavTransition = (width) => {
		  if (width > 768) {
		    nav.style.transition = "";
		  } else {
		    nav.style.transition = "transform 0.5s ease-in";
		  }
		};

		const handleResize = () => {
		  const width = event.target.innerWidth;
		  setNavTransition(width);
		};

		const init = () => {
		  window.addEventListener("resize", handleResize);
		  navSlide();
		};

		init();