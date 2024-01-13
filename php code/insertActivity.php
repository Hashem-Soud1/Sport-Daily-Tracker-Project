
<?php
$server_name = "localhost";
$user_name = "root";
$pass_server = "";
$db_name = "activeday_tracker";

$conn = new mysqli($server_name, $user_name, $pass_server, $db_name);

$userId = $_POST['userid'];
$acId = $_POST['activityid'];
$date = $_POST['date'];

if ($conn) {
    $sql = "SELECT * FROM user_activity WHERE user_Fk_id = '$userId' AND activity_Fk_id = '$acId'";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        $info["error"] = true;
        $info["message"] = "The activity has already been booked";
        echo json_encode($info);

    } else {
        // Check if $date is not empty before constructing the SQL query
        if (!empty($date)) {
            $sql2 = "INSERT INTO user_activity (activity_Fk_id, date, user_Fk_id) VALUES ('$acId', '$date', '$userId')";

            if ($conn->query($sql2) === TRUE) {
                $info["error"] = false;
                $info["message"] = "Add was successful";
                echo json_encode($info);

            } else {
                $info["error"] = true;
                $info["message"] = "Something went wrong!";
                echo json_encode($info);
            }
        } else {
            $info["error"] = true;
            $info["message"] = "Date cannot be empty";
            echo json_encode($info);
        }
    }

    $conn->close();
} else {
    echo("Error");
}


?>
