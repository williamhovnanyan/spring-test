<div id="header">
  <h2>Ticket</h2>
</div>
<div id="content">
    <table class="datatable">
        <tr>
          <th>Event</th>
          <th>Time</th>
          <th>Auditorium</th>
          <th>Seats</th>
          <th>Price</th>
        </tr>
        <tr>
            <td>${model["ticket"].getEvent().getName()}</td>
            <td>${model["ticket"].getEvent().getDateTime()}</td>
            <td>${model["ticket"].getEvent().getAuditorium().getName()}</td>
            <td>${model["ticket"].getSeats()}</td>
            <td>${model["ticket"].getPrice()}</td>
        </tr>
      </table>
</div>