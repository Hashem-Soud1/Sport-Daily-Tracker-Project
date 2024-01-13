<?php

$server_name="localhost";
$user_name="root";
$pass_server="";
$db_name="activeday_tracker"; 

 $Id=$_POST["userId"];

$conn=new mysqli($server_name,$user_name,$pass_server,$db_name);


if($conn){

$sql ="DELETE FROM user_info WHERE id = '$Id'";

       if($conn->query($sql)== TRUE) {
	
	
                $info["error"] = false;
				$info["message"]= "Account Deleted successfully";
				
				echo json_encode($info);
	}else{
		
		
		
		
		        $info["error"] = true;
				$info["message"]= "something goes wrong!";
				
				echo json_encode($info);
	}
	
	 $conn->close();
}


?>