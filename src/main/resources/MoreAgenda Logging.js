User: { //1xxx
	add: //11xx
	{
		ERROR: 1100,
		SUCCESSFUL: 1101,
		UNSUCCESSFUL: //1102x,
		{
			INVALID: //11021x
				{
					NAME: 110211,
					EMAIL: 110212,
					TELEPHONE: 110213,
					PASSWORD: 110214
				},
			EXISTING: 11022
		}
	},

	edit: //12xx
	 {
		ERROR: 1200,
		SUCCESSFUL: 1201,
		UNSUCCESSFUL: //1202x
		{
			INVALID: //12021x
			{
				NAME: 120211,
				EMAIL: 120212,
				TELEPHONE: 120213,
				PASSWORD: 120214,
			},
			EXISTING: 12022
		}
	},
		
	get: //13xx
	{
		ERROR: 1300,
		SUCCESSFUL: 1301,
		NO_SUCH: 1302
	},
		
	getAll: //14xx
	{
		ERROR: 1400,
		SUCCESSFUL: 1401,
		NO_REGISTRIES: 1402
	},

	logIn: //15xx
	{
		ERROR: 1500,
		SUCCESSFUL: 1501,
		NO_SUCH: 1502,
		UNSUCCESSFUL: 1503
	},

	logOut: //16xx
	{
		ERROR: 1600,
		SUCCESSFUL: 1601,
		UNSUCCESSFUL: 1602
	}

	associateMembership: //17xx
	{
		ERROR: 1700,
		SUCCESSFUL: 1701,
		NO_SUCH_USER: 1702,
		UNSUCCESSFUL: 1703
	},

	updateHours: //NO LOGS

	requestPasswordReset: //18xx
	{
		ERROR: 1800,
		SUCCESSFUL: 1801,
		NO_SUCH_USER: 1802,
		INVALID: 1803
	}

	resetPassword: //19xx
	{
		ERROR: 1900,
		SUCCESSFUL: 1901,
		NO_SUCH_USER: 1902,
		INVALID: //191x
		{
			PASSWORDS: 1911,
			UNEQUAL_PASSWORDS: 1912
		}
	}
},

Reservation: { //2xxx
	add: //21xx
	{
		ERROR: 2100,
		SUCCESSFUL: 2101,
		UNSUCCESSFUL: //2102x,
		{
			INVALID: //21021x
				{
					OLD_DATE: 210211,
					INVALID_DATE: 210212,
					OLD_DATE: 210213,
					INVALID_END_DATE: 210214,
					OLD_END_DATE: 210215,
					LEASABLE: // 210216X
					{
						UNAVAILABLE: 2102161,
						OCCUPIED: 2102162,
						OCCUPIED_RECURRENCE: 2102163
					}
				}
		}
	},

	edit: //22xx
	{
		ERROR: 2200,
		SUCCESSFUL: 2201,
		UNSUCCESSFUL: //2202x,
		{
			INVALID: //22021x
				{
					OLD_DATE: 220211,
					INVALID_DATE: 220212,
					OLD_DATE: 220213,
					INVALID_END_DATE: 220214,
					OLD_END_DATE: 220215,
					LEASABLE: // 220216X
					{
						UNAVAILABLE: 2202161,
						OCCUPIED: 2202162,
						OCCUPIED_RECURRENCE: 2202163
					}
				}
		}
	},
		
	get: //23xx
	{
		ERROR: 2300,
		SUCCESSFUL: 2301,
		NO_SUCH: 2302
	},
		
	getAll: //24xx
	{
		ERROR: 2400,
		SUCCESSFUL: 2401,
		NO_REGISTRIES: 2402
	},

	getRecurrences: //25xx
	{
		ERROR: 2500,
		SUCCESSFUL: 2501,
		NO_SUCH: 2502
	},

	cancel: //26xx
	{
		ERROR: 2600,
		single: { //2601x
			SUCCESSFUL: 26011,
			UNSUCCESSFUL: //26012x
			{
				NO_SUCH: 260121,
				PAST: 260122,
				EXPIRED: 260123
			}
		},
		recurrent: { //2602x
			SUCCESSFUL: 26021,
			UNSUCCESSFUL: //26022x
			{
				NO_SUCH: 260221,
				PAST: 260222,
				EXPIRED: 260223
			}
		}
	},

	concludeCurrent: //NO LOGS
},

Leasable: { //3xxx
	add: //31xx
	{
		ERROR: 3100,
		SUCCESSFUL: 3101,
		UNSUCCESSFUL: //3102x,
		{
			INVALID: //31021x
				{
					ADDRESS: 310211,
					INVALID_DATE: 210212,
					OLD_DATE: 210213,
					INVALID_END_DATE: 210214,
					OLD_END_DATE: 210215,
					CONSULTROOM: // 210216X
					{
						UNAVAILABLE: 2102161,
						OCCUPIED: 2102162,
						OCCUPIED_RECURRENCE: 2102163
					}
				}
		}
	},

	edit: //22xx
	{
		ERROR: 2200,
		SUCCESSFUL: 2201,
		UNSUCCESSFUL: //2202x,
		{
			INVALID: //22021x
				{
					OLD_DATE: 220211,
					INVALID_DATE: 220212,
					OLD_DATE: 220213,
					INVALID_END_DATE: 220214,
					OLD_END_DATE: 220215,
					CONSULTROOM: // 220216X
					{
						UNAVAILABLE: 2202161,
						OCCUPIED: 2202162,
						OCCUPIED_RECURRENCE: 2202163
					}
				}
		}
	},
		
	get: //23xx
	{
		ERROR: 2300,
		SUCCESSFUL: 2301,
		NO_SUCH: 2302
	},
		
	getAll: //24xx
	{
		ERROR: 2400,
		SUCCESSFUL: 2401,
		NO_REGISTRIES: 2402
	},

	getRecurrences: //25xx
	{
		ERROR: 2500,
		SUCCESSFUL: 2501,
		NO_SUCH: 2502
	},

	cancel: //26xx
	{
		ERROR: 2600,
		single: { //2601x
			SUCCESSFUL: 26011,
			UNSUCCESSFUL: //26012x
			{
				NO_SUCH: 260121,
				PAST: 260122,
				EXPIRED: 260123
			}
		},
		recurrent: { //2602x
			SUCCESSFUL: 26021,
			UNSUCCESSFUL: //26022x
			{
				NO_SUCH: 260221,
				PAST: 260222,
				EXPIRED: 260223
			}
		}
	},

	concludeCurrent: //NO LOGS
},
