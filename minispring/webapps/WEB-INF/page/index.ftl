<!DOCTYPE HTML>
<html>
<head>
    <title>${head.projectname}</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <script src="/javaee_minispring/utils/js/jquery.min.js"></script>
    <script src="/javaee_minispring/utils/js/config.js"></script>
    <script src="/javaee_minispring/utils/js/skel.min.js"></script>
    <link rel="stylesheet" href="/javaee_minispring/utils/css/skel-noscript.css"/>
    <link rel="stylesheet" href="/javaee_minispring/utils/css/style.css"/>
    <link rel="stylesheet" href="/javaee_minispring/utils/css/style-desktop.css"/>
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="/javaee_minispring/utils/css/ie9.css"/><![endif]-->
    <link rel="icon" href="/javaee_minispring/utils/images/minispringicon.icon">
    <!--[if lte IE 8]>
    <script src="/javaee_minispring/utils/js/html5shiv.js"></script>
    <link rel="stylesheet" href="/javaee_minispring/utils/css/ie8.css"/>
    <![endif]-->
    <!--[if lte IE 7]>
    <link rel="stylesheet" href="/javaee_minispring/utils/css/ie7.css"/><![endif]-->
</head>
<body>
<!-- Nav -->
<nav id="nav">
    <ul>
        <li><a href="#top">Introduce</a></li>
        <li><a href="#work">Modules</a></li>
        <li><a href="#portfolio">Functions</a></li>
        <li><a href="#contact">Touch Me</a></li>

    </ul>
</nav>
<!-- Home -->
<div class="wrapper wrapper-style1 wrapper-first">
    <article class="container" id="top">
        <div class="row">
            <div class="4u">
                <span class="me image image-full">
                    <img src="/javaee_minispring/utils/images/minispring.png" alt=""/><br/>
                     <p align="center"><a href="/javaee_minispring/test/downloadproject?name=${head.projectversion}"
                                          class="icon icon-download">Download</a></p>
                </span>
            </div>
            <div class="8u">
                <header>
                    <h1>Hi. I'm <strong>${head.projectname}</strong>.</h1>
                    <h2>Version ： ${head.projectversion},Author ：${head.author}</h2>
                </header>
                <p>
                    This is a simplified version of <strong>Spring Framework</strong>, which has realized the basic
                    functions.The functions are constantly improving.Please look forword to.
                </p>
                <a href="#work" class="button button-big">Learn about what I do</a>
            </div>
        </div>
    </article>
</div>

<!-- Work -->
<div class="wrapper wrapper-style2">
    <article id="work">
        <header>
            <h2>Module list</h2>
            <span>Some modules as follows</span>
        </header>
        <div class="container">
        <#--///遍历minispring模块-->
        <div class="row">
                    <#list modules as mo>
                        <div class="4u">
                            <section class="box box-style1">
                        <span class="me image image-centered"><img
                                src="${mo.imgpath}" alt=""/></span>
                                <h3><a href="${mo.linkurl}" target="_blank">${mo.name}</a></h3>
                                <p>
                                    ${mo.detail}
                                </p>
                            </section>
                        </div>
                        <#if (mo.index)>
                   </div>
                        <div class="row">
                        </#if>
                    </#list>
        <#--///-->
        </div>
            <footer>
                <p>Learn how this framework works?</p>
                <a href="#portfolio" class="button button-big">See some of my recent work</a>
            </footer>
    </article>
</div>

<!-- Portfolio -->
<div class="wrapper wrapper-style3">
    <article id="portfolio">
        <header>
            <h2>Awesome work makes happy clients.</h2>
            <span>Project can use hierarchical coding structure.</span>
        </header>
        <div class="container">
            <div class="row">
                <div class="12u">
                </div>
            </div>
        <#--///遍历功能-->
        <div class="row">
                     <#list functions as fun>
                         <div class="4u">
                             <article class="box box-style2">
                                 <a href="${fun.linkurl}" class="image image-full" target="_blank"><img
                                         src="${fun.imgpath}"
                                         alt=""/></a>
                                 <h3><a href="${fun.linkurl}" target="_blank">${fun.name}</a></h3>
                                 <p>${fun.detail}</p>
                             </article>
                         </div>
                         <#if (fun.index)>
                    </div>
                     <div class="row">
                         </#if>
                     </#list>
        <#--///-->
        </div>
            <footer>
                <p>Lorem ipsum dolor sit sapien vestibulum ipsum primis?</p>
                <a href="#contact" class="button button-big">Get in touch with me</a>
            </footer>
    </article>
</div>

<!-- Contact -->
<div class="wrapper wrapper-style4">
    <article id="contact">
        <header>
            <h2>Want to hire me? Get in touch!</h2>
            <span>Ornare nulla proin odio consequat  sapien vestibulum ipsum sed lorem.</span>
        </header>
        <div>
            <div class="row">
                <div class="12u">
                    <form method="post" action="#">
                        <div>
                            <div class="row half">
                                <div class="6u">
                                    <input type="text" name="name" id="name" placeholder="Name"/>
                                </div>
                                <div class="6u">
                                    <input type="text" name="email" id="email" placeholder="Email"/>
                                </div>
                            </div>
                            <div class="row half">
                                <div class="12u">
                                    <input type="text" name="subject" id="subject" placeholder="Subject"/>
                                </div>
                            </div>
                            <div class="row half">
                                <div class="12u">
                                    <textarea name="message" id="message" placeholder="Message"></textarea>
                                </div>
                            </div>
                            <div class="row">
                                <div class="12u">
                                    <a href="#" class="button form-button-submit">Send Message</a>
                                    <a href="#" class="button button-alt form-button-reset">Clear Form</a>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="row row-special">
                <div class="12u">
                    <h3>Find me on ...</h3>
                    <ul class="social">
                        <li class="twitter"><a href="" class="icon icon-twitter"><span>Twitter</span></a></li>
                        <li class="facebook"><a href="#" class="icon icon-facebook"><span>Facebook</span></a></li>
                        <li class="dribbble"><a href="" class="icon icon-dribbble"><span>Dribbble</span></a></li>
                        <li class="linkedin"><a href="#" class="icon icon-linkedin"><span>LinkedIn</span></a></li>
                        <li class="tumblr"><a href="#" class="icon icon-tumblr"><span>Tumblr</span></a></li>
                        <li class="googleplus"><a href="#" class="icon icon-google-plus"><span>Google+</span></a></li>
                        <li class="github"><a href="" class="icon icon-github"><span>Github</span></a></li>
                    </ul>
                </div>
            </div>
        </div>
        <footer>
            <p id="copyright">
                &copy; Copyright &copy; 2020.${head.author} All rights reserved.<a target="_blank"
                                                                                   href="">${head.projectname}</a>
            </p>
        </footer>
    </article>
</div>
</body>
</html>