Um email foi enviado para <b>${registration.email}</b>
<br/>
Clique neste
<a href="${link.action("RegistryControl").exec("confirm").add("code", registration.code).add("email", registration.email)}">link</a>
 