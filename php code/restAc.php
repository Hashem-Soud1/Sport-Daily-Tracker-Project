<?php 

$server_name="localhost";
$user_name="root";
$pass_server="";
$db_name="activeday_tracker"; 

$conn=new mysqli($server_name,$user_name,$pass_server,$db_name);

if($conn){
	
	
			 
			$sql2 = "UPDATE user_activity SET done =0";
			
			if($conn->query($sql2) == TRUE){
				$info["error"] = false;
				echo json_encode($info);
				
			}else{
				
				$info["error"] = true;
				echo json_encode($info);
				
			}
			
			$conn->close();
		}
		
	

 else{
	 echo("Error");
 }



?>