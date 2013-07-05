<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <fmt:setLocale value="${lang}"/>
    <fmt:setBundle basename="messages" var="bundle" scope="page"/>
    <title><fmt:message key="title.booking" bundle="${bundle}"/> - <fmt:message key="title.index"
                                                                                bundle="${bundle}"/></title>
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Bootstrap -->
    <link href="../static/css/bootstrap.min.css" rel="stylesheet">
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="../static/js/html5shiv.js"></script>
    <![endif]-->
    <link href="../static/css/style.css" rel="stylesheet">
</head>
<body id="body">
<div class="container">
    <%@include file="WEB-INF/tiles/header.jsp" %>
</div>
<table class="table table-bordered" border=1>
    <tr>
        <td width=10%>
            <form class="form-signin" action="Controller" method="POST">
                <h3 class="form-signin-heading">Выбор места</h3>
                <input type="hidden" name="action" value="booking">

                <table class="table table-bordered">
                    <tr class="success">
                        <th>Сектор:</th>
                    </tr>
                    <tr class="success">
                        <td>
                            <select id="SectorSelect" onchange="Sector_change(this.value)">
                                <option selected="selected" value="NONE">--Выберите--</option>
                                <option value="SECTOR_1">Сектор 1</option>
                                <option value="SECTOR_2">Сектор 2</option>
                                <option value="SECTOR_3">Сектор 3</option>
                                <option value="SECTOR_4">Сектор 4</option>
                                <option value="SECTOR_5">Сектор 5</option>
                            </select>
                        </td>
                    </tr>
                    <tr class="success">
                        <th>Ряд:</th>
                    </tr>
                    <tr class="success" style="display:none" id="choose_row">
                        <td>
                            <select id="row_select" onchange="Row_change(this.value)">
                            </select>
                        </td>
                    </tr>
                    <tr class="success">
                        <th>Место:</th>
                    </tr>
                    <tr class="success" style="display:none" id="choose_seat">
                        <td>
                            <select id="seat_select">
                            </select>
                        </td>
                    </tr>

                </table>
                <div>
                    <p>
                        <button type="submit" class="btn">Забронировать место</button>
                    </p>
                </div>
            </form>
        </td>
        <td>
            <img src="../static/img/hall_scheme.png" class="img-rounded">
        </td>
    </tr>
</table>
<h3 class="form-signin-heading">Забронированные билеты</h3>
<table class="table table-bordered">
    <tr class="success">
        <th>№</th>
        <th>Билет</th>
        <th>Цена</th>
        <th></th>
    </tr>
    <tr class="success">
        <td>1</td>
        <td>Портер, 3 ряд, 2 место</td>
        <td>55 000</td>
        <td>
            <button type="button" class="btn">Удалить</button>
        </td>
    </tr>
</table>
<div class="form-actions">
    <h3 class="form-signin-heading">Способ оплаты</h3>
    <label class="radio">
        <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>
        Курьерская доставка по городу Минску – наш оператор свяжется с Вами в течение 4-х рабочих дней
    </label>
    <label class="radio">
        <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
        Забронировать – Ваш билет закреплен за Вами в течение 24 часов (1-и сутки), выкупите его в любой из касс
    </label>
</div>
<div class="form-actions">
    <button type="submit" class="btn btn-primary">Сохранить изменения</button>
    <button type="button" class="btn">Отмена</button>
</div>
</form>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="..static/js/bootstrap.min.js"></script>
<script>
    var SectorSelect = document.getElementById("SectorSelect");
    var RowSelect = document.getElementById("row_select");
    var SeatSelect = document.getElementById("seat_select");
    var chooseRow = document.getElementById("choose_row");
    var chooseSeat = document.getElementById("choose_seat");

    var Sector = new Array();
    Sector[0] = 'Сектор 1';
    Sector[1] = 'Сектор 2';
    Sector[2] = 'Сектор 3';
    Sector[3] = 'Сектор 4';
    Sector[4] = 'Сектор 5';


    <c:forEach var="sector"  items="${tickets}">
        var Sector${sector.key}_rows = [];
        <c:forEach var="row"  items="${sector.value}" begin="0" varStatus="status">
            Sector${sector.key}_rows[${status.count}] = '${row.key}';
            var Sector${sector.key}_row${row.key}_seats = [];
            <c:forEach var="seat"  items="${row.value}" begin="0" varStatus="status">
                Sector${sector.key}_row${row.key}_seats[${status.count}] = '${seat}';
            </c:forEach>
        </c:forEach>
    </c:forEach>

    function shiftDownOption(oListbox, count) //Убираем 1-е пустое поле в selectе
    {
        if (count < oListbox.options.length) {
            for (var i = 0; i < oListbox.options.length - 1; i++) {
                var oOption = oListbox.options[i];
                var oNextOption = oListbox.options[i + 1];
                oListbox.insertBefore(oNextOption, oOption);
            }
            oListbox.options.length = oListbox.options.length - 1;
        }
    }

    function Sector_change(x) //При выборе сектора...
    {
        shiftDownOption(SectorSelect, 5); //Убираем 1-е пустое поле
        document.getElementById("choose_row").style.display = 'block';
        switch (x) {
            case "SECTOR_1":
            {
                RowSelect.options.length = 0;
                for (var i = 1; i < Sector1_rows.length; i++)
                    RowSelect.options[i] = new Option(Sector1_rows[i], Sector1_rows[i]);
                break;
            }
            case "SECTOR_2":
            {
                RowSelect.options.length = 0;
                for (var i = 1; i < Sector2_rows.length; i++)
                    RowSelect.options[i] = new Option(Sector2_rows[i], Sector2_rows[i]);
                break;
            }
            case "SECTOR_3":
            {
                RowSelect.options.length = 0;
                for (var i = 1; i < Sector3_rows.length; i++)
                    RowSelect.options[i] = new Option(Sector3_rows[i], Sector3_rows[i]);
                break;
            }
            case "SECTOR_4":
            {
                RowSelect.options.length = 0;
                for (var i = 1; i < Sector4_rows.length; i++)
                    RowSelect.options[i] = new Option(Sector4_rows[i], Sector4_rows[i]);
                break;
            }
            case "SECTOR_5":
            {
                RowSelect.options.length = 0;
                for (var i = 1; i < Sector5_rows.length; i++)
                    RowSelect.options[i] = new Option(Sector5_rows[i], Sector5_rows[i]);
                break;
            }
        }
    }
</script>
</body>
</html>