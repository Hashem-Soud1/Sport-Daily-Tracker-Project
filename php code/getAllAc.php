<?php

$server_name = "localhost";
$user_name = "root";
$pass_server = "";
$db_name = "activeday_tracker"; 

$conn = new mysqli($server_name, $user_name, $pass_server, $db_name);



if ($conn) {
    $sql = "SELECT * from activity";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $item["id"] = $row["id"];
            $item["name"] = $row["name"];
            $item["OptimalT"] = $row["OptimalT"];
            $item["Duration"] = $row["Duration"];
            $item["imag"] = $row["imag"];

            $activity[] = $item;
        }

        echo json_encode($activity);
    } else {
        $item["id"] = 0;
        $item["name"] = "NO Data";
        $item["OptimalT"] = "NO Data";
        $item["Duration"] = "NO Data"; 
        $item["imag"] = "NO Data";

        $activity[] = $item;

        echo json_encode($activity);
    }

    $conn->close();
} else {
    echo("Error");
}
?>
