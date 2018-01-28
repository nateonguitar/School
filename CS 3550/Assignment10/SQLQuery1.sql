DECLARE @State varchar(2) = 'UT';

SELECT COUNT(*) AS NumRentals, UnitID, PropertyName, PropertyState
FROM Unit un
	JOIN UnitType ut
	ON un.UnitTypeID = ut.UnitTypeID
	JOIN UnitRate ur
	ON ut.UnitTypeID = ur.UnitTypeID
	JOIN Property pr
	ON ur.PropertyID = pr.PropertyID
	JOIN Reservation re
	ON ur.UnitRateID = re.UnitRateID
WHERE pr.PropertyState = @State
GROUP BY pr.PropertyState, PropertyName, UnitID