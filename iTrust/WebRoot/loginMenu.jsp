<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaImpl" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaResponse" %>
    
<div class="panel panel-default">
	<div class="panel-heading">
		<h2 class="panel-title">Login</h2>
	</div>
	<div class="panel-body">
<script type="text/javascript">
function fillLoginFields(u,p) {
	document.getElementById("j_username").value = u;
	document.getElementById("j_password").value = p;		
	document.forms[0].submit();	
}
</script>
<%
	int failureCount = loginFailureAction.getFailureCount();
	if(failureCount != 0) {
		String msg = "Failed login attempts: " + failureCount;
		
%>
	<div style="align: center; margin-bottom: 10px;">
		<span class="iTrustError" style="font-size: 16px;"><%= StringEscapeUtils.escapeHtml("" + (msg)) %></span>
	</div>
<%
	}
%>

<%
	if(!loginFailureAction.needsCaptcha()){

%>
	<form method="post" action="/iTrust/login.jsp">
	MID<br />
	<input type="text" maxlength="10" id="j_username" name="j_username" style="width: 97%;"><br />
	Password<br />
	<input type="password" maxlength="20" id="j_password" name="j_password" style="width: 97%;"><br /><br />
	<input type="submit" value="Login">
		
	<br /><br />
	<a style="font-size: 80%;" href="/iTrust/util/resetPassword.jsp">Reset Password</a>

	</form>
<%
	} else {
%>
	<form method="post" action="/iTrust/login.jsp">
	MID<br />
	<input type="text" maxlength="10" name="j_username" style="width: 158px;"><br />
	Password<br />
	<input type="password" maxlength="20" name="j_password" style="width: 158px;"><br /><br />
<%

	ReCaptcha c = ReCaptchaFactory.newReCaptcha("6Lcpzb4SAAAAAHCD9njojEQJE3ZFuRVYQDsHdZjr", "6Lcpzb4SAAAAAGbscE39L3UmHQ_ferVd7RyJuo5Y", false);
	out.print(c.createRecaptchaHtml(null, null));

%>
	<input type="submit" value="Login"><br /><br />

	<a style="font-size: 80%;" href="/iTrust/util/resetPassword.jsp">Reset Password</a>

	</form>
<%
	}
if( ! "true".equals(System.getProperty("itrust.production") ) ) { 
%>
	<!-- This section is for testing purposes only!! -->
	<div class="panel panel-default">
	<div class="panel-heading">
		<h2 class="panel-title">Sample Users</h2>
	</div>
	<div class="panel-body">
	<table style="width:100%;" id="sampleLoginTable">
		<tr>
			<td>
				<a class="iTrustTestNavlink" onclick="fillLoginFields('5000000001','pw')" href="javascript:void(0)">LT 1</a>
			</td>
			<td>
				<a class="iTrustTestNavlink" onclick="fillLoginFields('5000000002','pw')" href="javascript:void(0)">LT 2</a>
			</td>
		</tr>
		<tr>
			<td>
				<a class="iTrustTestNavlink" onclick="fillLoginFields('1','pw')" href="javascript:void(0)">Patient 1</a>
			</td>
			<td>
				<a class="iTrustTestNavlink" onclick="fillLoginFields('2','pw')" href="javascript:void(0)">Patient 2</a>
			</td>
		</tr>
        <tr>
            <td>
                <a class="iTrustTestNavlink" onclick="fillLoginFields('5','pw')" href="javascript:void(0)">Patient 5</a>
            </td>
            <td>
                <a class="iTrustTestNavlink" onclick="fillLoginFields('22','pw')" href="javascript:void(0)">Patient 22</a>
            </td>
        </tr>
		<tr>
			<td>
				<a class="iTrustTestNavlink" onclick="fillLoginFields('9000000000','pw')" href="javascript:void(0)">HCP 1</a>
			</td>
            <td>
                <a class="iTrustTestNavlink" onclick="fillLoginFields('9000000003','pw')" href="javascript:void(0)">HCP 3</a>
            </td>
	    </tr>
	    <tr>
            <td>
                <a class="iTrustTestNavlink" onclick="fillLoginFields('9000000007','pw')" href="javascript:void(0)">HCP 7</a>
            </td>
			<td>
				<a class="iTrustTestNavlink" onclick="fillLoginFields('8000000009','uappass1')" href="javascript:void(0)">UAP</a>
			</td>
		</tr>
		<tr>
			<td>
				<a class="iTrustTestNavlink" onclick="fillLoginFields('9000000006','pw')" href="javascript:void(0)">ER</a>
			</td>
			<td>
				<a class="iTrustTestNavlink" onclick="fillLoginFields('7000000001','pw')" href="javascript:void(0)">PHA</a>
			</td>
		</tr>
		<tr>
			<td>
				<a class="iTrustTestNavlink" onclick="fillLoginFields('9000000001','pw')" href="javascript:void(0)">Admin</a>
			</td>
			<td>
				<a class="iTrustTestNavlink" onclick="fillLoginFields('9999999999','pw')" href="javascript:void(0)">Tester</a>
			</td>
		</tr>
		<tr>
			<td>
				<a class="iTrustTestNavlink" onclick="fillLoginFields('9000000012','pw')" href="javascript:void(0)">ObHCP0</a>
			</td>
			<td>
				<a class="iTrustTestNavlink" onclick="fillLoginFields('9000000085','pw')" href="javascript:void(0)">OptHCP0</a>
			</td>
		</tr>
		<tr>
			<td>
				<a class="iTrustTestNavlink" onclick="fillLoginFields('9000000086','pw')" href="javascript:void(0)">OphHCP0</a>
			</td>
		</tr>
	</table>
	</div>
<% } %>
    <form method="post" action="/iTrust/pre_register.jsp">
        <div style="padding-left:10px">
            <label>First Name*</label> <br/>
            <input type="text" name="firstName"/> <br/>

            <label>Last Name*</label> <br/>
            <input type="text" name="lastName"/> <br/>

            <label>Email*</label> <br/>
            <input type="email" name="email"/> <br/>

            <label>Password*</label> <br/>
            <input type="password" name="password" /> <br/>

            <label>Password Confirmation*</label> <br/>
            <input type="password" name="confirmPassword" /> <br/>

            <label>Address1</label> <br/>
            <input type="text" name="streetAddress1" /> <br/>

            <label>Address2</label> <br/>
            <input type="text" name="streetAddress2" /> <br/>

            <label>Phone</label> <br/>
            <input type="number" maxlength="10" name="phone" /> <br/>

            <label>Insurance Info</label> <br/>

            <label>Insurance Provider Name</label> <br/>
            <input type="text" name="icName" /> <br/>

            <label>Insurance Provider Address1</label> <br/>
            <input type="text" name="icAddress1" /> <br/>

            <label>Insurance Provider Address2</label> <br/>
            <input type="text" name="icAddress2" /> <br/>

            <label>Insurance Provider Phone</label> <br/>
            <input type="number" maxlength="10" name="icPhone" /> <br/>

            <label>Height (inches)</label> <br/>
            <input type="number" maxlength="3" name="height" /> <br/>

            <label>Weight (pounds)</label> <br/>
            <input type="number" maxlength="3" name="weight" /> <br/>

            <label>Smoker?</label> <br/>
            <input type="radio" id="smoker1Choice" name="smoker" value="true"/>
            <label for="smoker1Choice">Yes</label>

            <input type="radio" id="smoker2Choice" name="smoker" value="false"/>
            <label for="smoker2Choice">No</label>

            <br/>
            <button type="submit" id="preregistSubmit">(Pre)register</button>
            </div>
    </form>

</div>
</div>
</div>
<script type="text/javascript">
	document.forms[0].j_username.focus();
</script>
<%
%>
