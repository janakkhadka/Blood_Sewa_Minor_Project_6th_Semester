export const activityHistory = [
  { date: new Date(2023, 4, 15), event: "Central Hospital", activity: "Donation" },
  { date: new Date(2023, 1, 1), event: "Community Center", activity: "Volunteering" },
  { date: new Date(2022, 9, 10), event: "Mobile Blood Drive", activity: "Donation/Volunteering" },
  { date: new Date(2023, 5, 25), event: "Downtown Blood Bank", activity: "Donation" },
  { date: new Date(2023, 2, 18), event: "City Park Blood Drive", activity: "Volunteering" },
  { date: new Date(2023, 3, 12), event: "Health Fair Donation Booth", activity: "Donation/Volunteering" },
  { date: new Date(2023, 6, 9), event: "Red Cross Blood Drive", activity: "Donation" },
  { date: new Date(2023, 7, 30), event: "Local School Donation Event", activity: "Volunteering" },
  { date: new Date(2023, 8, 14), event: "Corporate Blood Donation Camp", activity: "Donation/Volunteering" },
  { date: new Date(2023, 10, 5), event: "Community Health Center", activity: "Donation" },
  { date: new Date(2023, 11, 22), event: "Regional Hospital", activity: "Donation/Volunteering" },
];



  export const urgentBlood = [
    { date: new Date(2023, 4, 15), location: "Central Hospital, New York", bloodType: "A+" },
    { date: new Date(2023, 1, 1), location: "Community Center, Los Angeles", bloodType: "A+" },
    { date: new Date(2022, 9, 10), location: "Mobile Blood Drive, Chicago", bloodType: "A+" },
    { date: new Date(2023, 5, 25), location: "Downtown Blood Bank, Houston", bloodType: "B-" },
    { date: new Date(2023, 2, 18), location: "City Park Blood Drive, Phoenix", bloodType: "O+" },
    { date: new Date(2023, 3, 12), location: "Health Fair Donation Booth, Philadelphia", bloodType: "AB+" },
    { date: new Date(2023, 6, 9), location: "Red Cross Blood Drive, San Antonio", bloodType: "A-" },
    { date: new Date(2023, 7, 30), location: "Local School Donation Event, San Diego", bloodType: "O-" },
    { date: new Date(2023, 8, 14), location: "Corporate Blood Donation Camp, Dallas", bloodType: "B+" },
    { date: new Date(2023, 10, 5), location: "Community Health Center, San Jose", bloodType: "AB-" },
    { date: new Date(2023, 11, 22), location: "Regional Hospital, Austin", bloodType: "O+" },
  ];
  

  export const donorList = [
    { sn: 1, name: "John Doe", bloodGroup: "A+" },
    { sn: 2, name: "Jane Smith", bloodGroup: "O-" },
    { sn: 3, name: "Alice Johnson", bloodGroup: "B+" },
    { sn: 4, name: "Robert Brown", bloodGroup: "AB-" },
    { sn: 5, name: "Michael Green", bloodGroup: "A-" },
    { sn: 6, name: "Emily Davis", bloodGroup: "O+" },
    { sn: 7, name: "David Wilson", bloodGroup: "B-" },
    { sn: 8, name: "Sophia Lee", bloodGroup: "AB+" },
    { sn: 9, name: "James Taylor", bloodGroup: "O+" },
    { sn: 10, name: "Emma White", bloodGroup: "A-" },
    { sn: 11, name: "Oliver Harris", bloodGroup: "B+" },
    { sn: 12, name: "Isabella Clark", bloodGroup: "AB-" },
    { sn: 13, name: "Liam Martinez", bloodGroup: "A+" },
    { sn: 14, name: "Mia Rodriguez", bloodGroup: "O-" },
    { sn: 15, name: "Noah Lewis", bloodGroup: "B+" },
    { sn: 16, name: "Charlotte Hall", bloodGroup: "AB+" },
  ];
  
  
  
  
export const events = [
    { id: "1", title: "Community Blood Drive", date: new Date(2023, 6, 1), location: "City Hall" },
    { id: "2", title: "Hospital Donation Day", date: new Date(2023, 6, 15), location: "Central Hospital" },
    { id: "3", title: "University Blood Donation", date: new Date(2023, 7, 5), location: "State University" },
    { id: "4", title: "Corporate Blood Camp", date: new Date(2023, 8, 10), location: "Tech Park HQ" },
    { id: "5", title: "Community Health Fair", date: new Date(2023, 9, 20), location: "Community Center" },
    { id: "6", title: "Blood Donation Awareness", date: new Date(2023, 10, 1), location: "Downtown Plaza" },
    { id: "7", title: "Local Blood Drive", date: new Date(2023, 10, 15), location: "Town Square" },
    { id: "8", title: "Youth Blood Campaign", date: new Date(2023, 11, 5), location: "High School Auditorium" },
    { id: "9", title: "Holiday Blood Drive", date: new Date(2023, 11, 20), location: "Community Hall" },
    { id: "10", title: "Emergency Blood Drive", date: new Date(2024, 0, 5), location: "Emergency Services HQ" },
    { id: "11", title: "Winter Blood Camp", date: new Date(2024, 1, 10), location: "Sports Complex" },
    { id: "12", title: "Annual Donor Meetup", date: new Date(2024, 2, 15), location: "Convention Center" },
    { id: "13", title: "Regional Blood Drive", date: new Date(2024, 3, 1), location: "Regional HQ" },
    { id: "14", title: "Spring Donation Event", date: new Date(2024, 4, 20), location: "Botanical Garden" },
    { id: "15", title: "Volunteer Blood Drive", date: new Date(2024, 5, 15), location: "Volunteer Center" },
  ];

  export const notifications = [
    {id: "1", name: "A+ Blood needed near Balkhu Kathmandu"},
    {id: "1", name: "A+ Blood needed near Balkhu"},
    {id: "1", name: "A+ Blood needed near Balkhu"},
    {id: "1", name: "A+ Blood needed near Balkhu"},
    {id: "1", name: "A+ Blood needed near Balkhu"},
    {id: "1", name: "A+ Blood needed near Balkhu"},
    {id: "1", name: "A+ Blood needed near Balkhu"},
  ]

  export const pastEvents = [
    { 
      id: "1", 
      title: "Community Blood Drive", 
      date: new Date(2023, 6, 1), 
      location: "City Hall", 
      donorNumber: 50, 
      bloodType: { 
        "A+": 10, "A-": 5, "B+": 8, "B-": 2, "AB+": 3, "AB-": 2, "O+": 15, "O-": 5 
      } 
    },
    { 
      id: "2", 
      title: "Hospital Donation Day", 
      date: new Date(2023, 6, 15), 
      location: "Central Hospital", 
      donorNumber: 80, 
      bloodType: { 
        "A+": 20, "A-": 10, "B+": 15, "B-": 5, "AB+": 8, "AB-": 2, "O+": 15, "O-": 5 
      } 
    },
    { 
      id: "3", 
      title: "University Blood Donation", 
      date: new Date(2023, 7, 5), 
      location: "State University", 
      donorNumber: 70, 
      bloodType: { 
        "A+": 18, "A-": 7, "B+": 15, "B-": 3, "AB+": 5, "AB-": 2, "O+": 18, "O-": 2 
      } 
    },
    { 
      id: "4", 
      title: "Corporate Blood Camp", 
      date: new Date(2023, 8, 10), 
      location: "Tech Park HQ", 
      donorNumber: 60, 
      bloodType: { 
        "A+": 15, "A-": 5, "B+": 10, "B-": 4, "AB+": 3, "AB-": 2, "O+": 18, "O-": 3 
      } 
    },
    { 
      id: "5", 
      title: "Community Health Fair", 
      date: new Date(2023, 9, 20), 
      location: "Community Center", 
      donorNumber: 65, 
      bloodType: { 
        "A+": 18, "A-": 6, "B+": 15, "B-": 5, "AB+": 4, "AB-": 2, "O+": 12, "O-": 3 
      } 
    },
    { 
      id: "6", 
      title: "Blood Donation Awareness", 
      date: new Date(2023, 10, 1), 
      location: "Downtown Plaza", 
      donorNumber: 55, 
      bloodType: { 
        "A+": 12, "A-": 4, "B+": 10, "B-": 3, "AB+": 5, "AB-": 1, "O+": 15, "O-": 5 
      } 
    },
    { 
      id: "7", 
      title: "Local Blood Drive", 
      date: new Date(2023, 10, 15), 
      location: "Town Square", 
      donorNumber: 50, 
      bloodType: { 
        "A+": 15, "A-": 5, "B+": 12, "B-": 4, "AB+": 5, "AB-": 1, "O+": 10, "O-": 3 
      } 
    },
    { 
      id: "8", 
      title: "Youth Blood Campaign", 
      date: new Date(2023, 11, 5), 
      location: "High School Auditorium", 
      donorNumber: 60, 
      bloodType: { 
        "A+": 20, "A-": 5, "B+": 10, "B-": 4, "AB+": 5, "AB-": 2, "O+": 12, "O-": 2 
      } 
    },
    { 
      id: "9", 
      title: "Holiday Blood Drive", 
      date: new Date(2023, 11, 20), 
      location: "Community Hall", 
      donorNumber: 75, 
      bloodType: { 
        "A+": 18, "A-": 7, "B+": 15, "B-": 4, "AB+": 6, "AB-": 3, "O+": 18, "O-": 4 
      } 
    },
    { 
      id: "10", 
      title: "Emergency Blood Drive", 
      date: new Date(2024, 0, 5), 
      location: "Emergency Services HQ", 
      donorNumber: 85, 
      bloodType: { 
        "A+": 25, "A-": 8, "B+": 20, "B-": 6, "AB+": 10, "AB-": 3, "O+": 10, "O-": 3 
      } 
    },
    { 
      id: "11", 
      title: "Winter Blood Camp", 
      date: new Date(2024, 1, 10), 
      location: "Sports Complex", 
      donorNumber: 90, 
      bloodType: { 
        "A+": 30, "A-": 10, "B+": 15, "B-": 8, "AB+": 5, "AB-": 5, "O+": 10, "O-": 7 
      } 
    },
    { 
      id: "12", 
      title: "Annual Donor Meetup", 
      date: new Date(2024, 2, 15), 
      location: "Convention Center", 
      donorNumber: 95, 
      bloodType: { 
        "A+": 25, "A-": 10, "B+": 20, "B-": 7, "AB+": 8, "AB-": 5, "O+": 15, "O-": 5 
      } 
    },
    { 
      id: "13", 
      title: "Regional Blood Drive", 
      date: new Date(2024, 3, 1), 
      location: "Regional HQ", 
      donorNumber: 100, 
      bloodType: { 
        "A+": 35, "A-": 12, "B+": 18, "B-": 10, "AB+": 7, "AB-": 4, "O+": 10, "O-": 4 
      } 
    },
    { 
      id: "14", 
      title: "Spring Donation Event", 
      date: new Date(2024, 4, 20), 
      location: "Botanical Garden", 
      donorNumber: 70, 
      bloodType: { 
        "A+": 20, "A-": 5, "B+": 15, "B-": 6, "AB+": 4, "AB-": 2, "O+": 12, "O-": 6 
      } 
    },
    { 
      id: "15", 
      title: "Volunteer Blood Drive", 
      date: new Date(2024, 5, 15), 
      location: "Volunteer Center", 
      donorNumber: 65, 
      bloodType: { 
        "A+": 18, "A-": 5, "B+": 10, "B-": 5, "AB+": 5, "AB-": 3, "O+": 15, "O-": 4 
      } 
    },
  ];
  
  