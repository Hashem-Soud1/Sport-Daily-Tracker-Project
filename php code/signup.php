<?php
    $servername = "localhost";
	$username = "root";
	$password= "";
	$db_name = "activeday_tracker";
	
	$Name = $_POST["name"];
	$Pass = $_POST["password"];	
	
	$conn = new mysqli($servername,$username,$password,$db_name);
	
	if($conn->connect_error){
		
		
		
	}else{
		
		//echo "connected successfully :)"."<br>";
		$sql = "SELECT * FROM user_info WHERE name = '$Name' AND password = '$Pass' ";
		$result = $conn->query($sql);
		if($result -> num_rows>0){
			
				$user = array(
				
				"name" => "no data",
				"password" => "no data",
				
				);
				$info["error"] = true;
				$info["message"]= "username or email already exist";
				$info["user"] = $user;
				
				echo json_encode($info);
				
				
		}else{
			
			
			
			
			$sql2 = "INSERT INTO user_info (name,password) VALUES('$Name','$Pass')";
			if($conn->query($sql2) === TRUE){
				
			
				$user = array(
		
				"name" => $Name,
				"password" => $Pass,
				);
				$info["error"] = false;
				$info["message"]= "User Registed successfully";
				$info["user"] = $user;
				
				echo json_encode($info);
				
				
			}else{
				
				$user = array(
				"name" => "no data",
				"password" => "no data",
				);
				$info["error"] = true;
				$info["message"]= "something goes wrong!";
				$info["user"] = $user;
				
				echo json_encode($info);
				
			}
			
			
		}
	
	}

?>