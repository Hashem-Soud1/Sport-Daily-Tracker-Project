<?php
    $servername = "localhost";
	$username = "root";
	$password= "";
	$db_name = "activeday_tracker";
	
     $userId = $_GET["userid"];
	 $acId = $_GET["activityid"];
	
	
	
	$conn = new mysqli($servername,$username,$password,$db_name);
	
	if($conn){
		
		$sql="DELETE FROM user_activity WHERE activity_Fk_id ='$acId' AND user_Fk_id = '$userId'";
		
		if ($conn->query($sql)==true) {
			
			    $result["error"] = false;
				$result["message"]= "Your activity has been successfully cancelled";
				echo json_encode($result);
		}
		else {
			    $result["error"] = true;
				$result["message"]= "something goes wrong!";
				echo json_encode($result);
		}
	
	$conn->close();
	}
	
	
	else{
		echo("Error");
	}

?>