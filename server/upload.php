<?php
echo "php started";
if($_SERVER['REQUEST_METHOD']=='POST'){
 var_dump($_FILES);
 var_dump($_POST);
 //trying to save the file in the directory 
 try{
 //saving the file 
for($i=0;$i<sizeof($_FILES);$i++){
$name=$_POST['myname'.$i];
$size=$_FILES['image'.$i]['size'];
$type=$_FILES['image'.$i]['type'];
echo $name." ".$size." ".$type;
   if($size<5000000){
	if(move_uploaded_file($_FILES['image'.$i]['tmp_name'],"./images/".$name.".jpg")){
              echo "file uploaded";
          }else{
             echo "not uploaded";
           }
   }else{
	$response['error']=true;
	$response['message']='wrong file format';
   }


 }
 //if some error occurred 
 }catch(Exception $e){
 } 
 //displaying the response 
 
 
 //closing the connection 
 
 }else{
 $response['error']=true;
 $response['message']='Please choose a file';
 }

 
 
?>
 <html>
<head>
	<title></title>
</head>
<body>
<form method="POST" action="<?php echo $_SERVER['PHP_SELF']; ?>" enctype="multipart/form-data">
	<input type="file" name="image">
<input type="submit">
</form>
</body>
</html>