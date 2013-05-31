<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 5/20/13
  Time: 11:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Название Спектакля - Театр Minsk Opera House</title>
    <meta charset="utf-8">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Bootstrap -->
    <link href="userView/css/bootstrap.min.css" rel="stylesheet">
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <![endif]-->
    <link href="css/style.css" rel="stylesheet">
</head>
<body id="body">
<div class="container">
    <jsp:include page="Head.jsp"></jsp:include>

    <div class="contentWrapper">
        <div class="row">
            <div class="span12">
                <div  class="well well-small">
                    <div class="row">
                        <div class="span8">
                            <h1>Аида <small>Джузеппе Верди</small></h1>
                            <p>Опера в 4-х действиях с участием народного артиста Грузии Теймураза Гугушвили</p>
                            <dl class="dl-horizontal">
                                <dt>Либретто</dt>
                                <dd>Антонио Гисланцони, Камилл дю Локль
                                    по сценарию Франсуа Огюста Фердинанда Мариетта</dd>
                                <dt>Дирижер-постановщик</dt>
                                <dd>Вячеслав Волич</dd>
                                <dt>Режиссер-постановщик</dt>
                                <dd>Михаил Панджавидзе</dd>
                            </dl>
                            <p>
                                <a href="#" class="btn btn-mini">Купить билет</a>
                            </p>
                        </div>
                        <div class="span3">
                            <img src="img/thumbnail/1.jpg" class="img-rounded">
                        </div>
                    </div>
                </div>
                <p>
                    <b>Декорации и костюмы</b> – народный художник Беларуси, лауреат Государственной премии СССР Евгений Чемодуров<br />
                    <b>Хормейстер-постановщик</b> – народная артистка Беларуси Нина Ломанович<br />
                    <b>Художник-постановщик</b> – Александр Костюченко<br />
                    <b>Художник по костюмам</b> – Екатерина Булгакова<br />
                    <b>Балетмейстер-постановщик</b> – Александра Тихомирова<br />
                    <b>Художник по свету</b> – Сергей Шевченко<br />
                    <b>Дирижеры</b> – Иван Костяхин, Андрей Галанов<br />
                </p>
                <p>
                    Исполняется на итальянском языке<br />
                    Продолжительность – 3 часа 50 минут с тремя антрактами<br />
                    Премьера – 30 мая 2011 года<br />
                </p>
                <p class="lead">
                    Cпектакль-лауреат II Национальной театральной премии Республики Беларусь в номинации "Лучший музыкальный спектакль"
                </p>
                <p class="lead">
                    В партии Радамеса - народный артист Грузии ведущий солист Тбилисского академического театра оперы и балета им. З.Палиашвили Теймураз Гугушвили
                </p>
            </div>
        </div>

        <hr>

        <div class="footer">
            <p>&copy; Java Enterprise 2013.3 Team 1 Project</p>
        </div>
    </div>
</div>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>