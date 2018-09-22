<div id="header">
  <h2>Users</h2>
</div>
<div id="content">
    <table class="datatable">
        <tr>
          <th>Id</th>
          <th>Name</th>
          <th>Email</th>
          <th>BirthDay</th>
        </tr>
        <#list model["users"] as user>
            <#if user??>
                <tr>
                    <td>${user.getId()}</td>
                    <td>${user.getName()}</td>
                    <td>${user.getEmail()}</td>
                    <td>${user.getBirthday()}</td>
                </tr>
            </#if>
        </#list>
      </table>
</div>