<?php 

$server_name="localhost";
$user_name="root";
$pass_server="";
$db_name="activeday_tracker"; 

$conn=new mysqli($server_name,$user_name,$pass_server,$db_name);

  $userId = $_POST['uid'];
  $acId = $_POST['aid'];
  $date = $_POST['date'];
  

if($conn){
	
	
	$checkSql = "SELECT * FROM prev_results WHERE activity_Fk_id = '$acId' AND date2 = '$date'";

	$result = $conn->query($checkSql);
		if($result -> num_rows>0){
			
				$info["error"] = true;
				$info["message"]= "The activity has already is done";
			
				echo json_encode($info);
		} else{
	
	
			$sql = " INSERT INTO prev_results (name, Duration,activity_Fk_id,user_Fk_id, date2) SELECT name,Duration,$acId,$userId,'$date'  FROM activity WHERE id = '$acId'";
			
			if($conn->query($sql) == TRUE){
				$info["error"] = false;
				echo json_encode($info);
				
			}else{
				
				$info["error"] = true;
				echo json_encode($info);
				
			}
			
			$conn->close();
		}
		}
		
	

 else{
	 echo("Error");
 }



?>