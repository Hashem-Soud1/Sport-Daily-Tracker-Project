<?php

$server_name = "localhost";
$user_name = "root";
$pass_server = "";
$db_name = "activeday_tracker"; 

$conn = new mysqli($server_name, $user_name, $pass_server, $db_name);


$userId = 16;

if ($conn) {
    $sql = "SELECT * from prev_results WHERE user_Fk_id = '$userId'";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
     
            $item["name"] = $row["name"];
            $item["Duration"] = $row["Duration"];
            $item["date"] = $row["date2"];
         
            $activity[] = $item;
        }

        echo json_encode($activity);
    } else {
      
        $item["name"] = "NO Data";
        $item["date"] = "NO Data";
        $item["Duration"] = "NO Data"; 
       

        $activity[] = $item;

        echo json_encode($activity);
    }

    $conn->close();
} else {
    echo("Error");
}
?>
