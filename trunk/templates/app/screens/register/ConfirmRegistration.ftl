<div class="notification info">
	Um email foi enviado para <b>${registration.email}</b>.
	<br/>
	Por favor, siga as instruções no email para continuar seu cadastro.
</div>

<@g.debug>
	Clique neste
	<a 	id="linkWithconfirmationCode"
		href="${link.action("RegistryControl").exec("confirm").add("code", registration.code).add("email", registration.email)}">link</a>
</@g.debug> 