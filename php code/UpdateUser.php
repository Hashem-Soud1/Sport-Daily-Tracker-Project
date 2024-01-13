<?php

    $servername = "localhost";
	$username = "root";
	$password= "";
	$db_name = "activeday_tracker";
	
	$ID = $_POST["id"];

	$Name = $_POST["name"];
	$Pass = $_POST["password"];
		
	
	$conn = new mysqli($servername,$username,$password,$db_name);
	
	if($conn->connect_error){
		
		
		//die("not connected successfully :(");
	
	}else{
		
		
	
	
		$sql = "UPDATE user_info SET name = '$Name', password = '$Pass' WHERE id = '$ID'";
		
		if($conn ->query($sql) === TRUE){
			
			
			//echo "record updated successfully"."<br>";
			$user = array(
				
				"name" => $Name,
				"password" => $Pass
				
				);
				$info["error"] = false;
				$info["message"]= "User Info updated successfully";
				$info["user"] = $user;
				
				echo json_encode($info);
		}else{
			
			//echo "something goes wrong"."<br>";
			$user = array(
				
				"name" => "no data",
				"password" => "no data"
				
				);
				$info["error"] = true;
				$info["message"]= "something goes worng - User Info not updated correctly!";
				
				$info["user"] = $user;
				
				echo json_encode($info);
				
		}
	}
?>