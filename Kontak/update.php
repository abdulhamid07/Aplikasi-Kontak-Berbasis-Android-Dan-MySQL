<?php  

	if ($_SERVER['REQUEST_METHOD'] == 'POST') {
		
		$id = $_POST['id'];
		$name = $_POST['nama_kontak'];
		$telp = $_POST['no_telp'];

		$sql = "UPDATE tb_kontak SET nama_kontak = '$name',no_telp = '$telp' WHERE id = '$id'";

		require_once('koneksi.php');

		if (mysqli_query($con,$sql)) {
			echo "Berhasil";
		}else{
			echo mysqli_error();
		}

		mysqli_close($con);
	}
?>