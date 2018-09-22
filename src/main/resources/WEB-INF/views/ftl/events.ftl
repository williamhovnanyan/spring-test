<div id="header">
  <h2>Events</h2>
</div>
<div id="content">
    <table class="datatable">
        <tr>
          <th>Id</th>
          <th>Name</th>
          <th>Time</th>
          <th>Rate</th>
          <th>Auditorium</th>
          <th>Base Price</th>
        </tr>
        <#list model["events"] as event>
            <tr>
                <td>${event.getId()}</td>
                <td>${event.getName()}</td>
                <td>${event.getDateTime()}</td>
                <td>${event.getRate()}</td>
                <td><#if event.getAuditorium()??>${event.getAuditorium().getName()}<#else>Has not yet assigned</#if></td>
                <td>${event.getBasePrice()}</td>
            </tr>
        </#list>
      </table>
</div>