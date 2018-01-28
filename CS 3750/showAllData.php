<?php include("scripts/connection.php"); ?>
<html>
<head>
	<style type="text/css">
		table{
			text-align:center;
		}
	</style>
</head>
<body>
	<h1>Users</h1>
	<table>
		<tr>
			<th>userID</th>
			<th>userFirstName</th>
			<th>userLastName</th>
			<th>userEmail</th>
			<th>userUsername</th>
			<th>userPhoneNumber</th>
			<th>userPassword</th>
			<th>userRole</th>   <!-- null or 0 => normal user, 1 => admin -->
			<th>userBanned</th> <!-- flag as 1 if banned -->
		</tr>
		<?php
		$sql = "SELECT * FROM Users;";
		
		if($result = $mysqli->query($sql))
		{
			while($row = $result->fetch_array())
			{
				?>
				<tr>
					<?php
					echo   "<td>" . $row['userID'] . "</td>
							<td>" . $row['userFirstName'] . "</td>
							<td>" . $row['userLastName'] . "</td>
							<td>" . $row['userEmail'] . "</td>
							<td>" . $row['userUsername'] . "</td>
							<td>" . $row['userPhoneNumber'] . "</td>
							<td>" . $row['userPassword'] . "</td>
							<td>" . $row['userRole'] . "</td>
							<td>" . $row['userBanned'] . "</td>";
					?>
				</tr>
			<?php
			}
			$result->close();
		}
		?>
	</table>
	
	<hr />
	
	<h1>Auctions</h1>
	<table>
		<tr>
			<th>auctionID</th>
			<th>userID</th><!-- the user who created this auction -->
			<th>auctionCreationDateTime</th>
			<th>auctionExpireDateTime</th>
			<th>auctionDescription</th>
		</tr>
		<?php
		$sql = "SELECT * FROM Auctions;";
		
		if($result = $mysqli->query($sql))
		{
			while($row = $result->fetch_array())
			{
				?>
				<tr>
					<?php
					echo   "<td>" . $row['auctionID'] . "</td>
							<td>" . $row['userID'] . "</td>
							<td>" . $row['auctionCreationDateTime'] . "</td>
							<td>" . $row['auctionExpireDateTime'] . "</td>
							<td>" . $row['auctionDescription'] . "</td>";
					?>
				</tr>
			<?php
			}
			$result->close();
		}
		?>
	</table>
	
	<hr />
	
	<h1>Auction Items</h1>
	<table>
		<tr>
			<th>auctionItemID</th>
			<th>auctionID</th><!-- the Auction this Auction Item belongs to -->
			<th>userID</th><!-- the user that has the current highest bid -->
			<th>auctionItemCurrentBid</th>
			<th>auctionItemMinimumBid</th>
		</tr>
		<?php
		$sql = "SELECT * FROM AuctionItems;";
		
		if($result = $mysqli->query($sql))
		{
			while($row = $result->fetch_array())
			{
				?>
				<tr>
					<?php
					echo   "<td>" . $row['auctionItemID'] . "</td>
							<td>" . $row['auctionID'] . "</td>
							<td>" . $row['userID'] . "</td>
							<td>" . $row['auctionItemCurrentBid'] . "</td>
							<td>" . $row['auctionItemMinimumBid'] . "</td>";
					?>
				</tr>
			<?php
			}
			$result->close();
		}
		?>
	</table>
	
	<hr />
	
	<h1>Images</h1>
	<table>
		<tr>
		


			<th>imageID</th>
			<th>auctionItemID</th><!-- the AuctionItem this Image belongs to -->
			<th>imagePath</th>
			
		</tr>
		<?php
		$sql = "SELECT * FROM Images;";
		
		if($result = $mysqli->query($sql))
		{
			while($row = $result->fetch_array())
			{
				?>
				<tr>
					<?php
					echo   "<td>" . $row['imageID'] . "</td>
							<td>" . $row['auctionItemID'] . "</td>
							<td>" . $row['imagePath'] . "</td>";
					?>
				</tr>
			<?php
			}
			$result->close();
		}
		?>
	</table>
</body>
</html>