<html>
<head>
    <#include "/common/head.ftl">
    <#include "/common/splinter.ftl">
    <title>No Admittance Except on Party Business</title>
</head>
<body>
<div class="content">
<p>
Nicely done! You're almost there. Let's just make sure that you aren't:
<ul>
<li/> A time-traveling robot
<li/> An intellectual-property lawyer
<li/> An FBI agent
<li/> Or an Interpol operative
</ul>
</p>
<#if error??>
    <p style="color:red;">${error}</p>
</#if>
<form action="/" method="post" enctype="application/x-www-form-urlencoded">

<p>
<div class="col">
<#if turtle??>
    <div style="color:red;">${turtle}</div>
</#if>
<label for="turtle">Who is the best Ninja Turtle?</label>
<input type="text" name="turtle" id="turtle" />
</div>
</p>

<p>
<div class="col">
<#if country??>
    <div style="color:red;">${country}</div>
</#if>
<label for="country">What foreign country has awesome hiking?</label>
<input type="text" name="country" id="country" />
</div>
</p>

<p>
<div class="col">
<#if box??>
    <div style="color:red;">${box}</div>
</#if>
<label for="quantity">How many tattoos did you get?</label>
<input type="number" id="box" name="box" min="0">
</div>
</p>

<div class="col">
<input type="submit" value="Submit" />
</div>
</form>
</div>
</body>
</html>
