<?php


	$servername = "localhost";
	$username = "root";
	$password= "";
	$db_name = "activeday_tracker";
	
	$name = $_POST["name"];
	$Pass = $_POST["password"];
	
	
 
   // echo "Email >>> ".$Email."<br>";
   // echo "pass >>> ".$Pass."<br>";
	
	
	$conn = new mysqli($servername,$username,$password,$db_name);
	
	if($conn->connect_error){
		
		
		//die("not connected successfully :(");
		
	}else{
		
		
		//echo "connected successfully :)";
		$sql = "SELECT * FROM user_info WHERE name ='$name' AND password = '$Pass'";
		$result = $conn->query($sql);
		if($result ->num_rows>0){
			
			//echo "some data selected";
			while($row = $result -> fetch_assoc()){
				
				$user = array(
				
				"id" => $row["id"],
				"name" => $row["name"],
				"password" => $row["password"]
				
				);
				
				$info["error"] = false;
				$info["message"]= "Loged In Successfully";
				$info["user"] = $user;
				
				
				
				
			}
			
			 echo json_encode($info);
			
			
			
		}else{
			
			
				$user = array(
				
				"id" => 0,
				"name" => "No Data",
				"password" => "No Data"
				
				);
				
				$info["error"] = true;
				$info["message"]= " Faield to Logging";
				$info["user"] = $user;
			
			echo json_encode($info);
		}
		
	}

?>