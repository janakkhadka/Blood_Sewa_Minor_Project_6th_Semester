import React from "react";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  Cell
} from "recharts";

const defaultBarList = [  //bpkish  ko data ho
      { type: 'A+', pint: 45 },
      { type: 'A-', pint: 20 },
      { type: 'B+', pint: 60 },
      { type: 'B-', pint: 15 },
      { type: 'AB+', pint: 30 },
      { type: 'AB-', pint: 10 },
      { type: 'O+', pint: 50 },
      { type: 'O-', pint: 25 }
];

const MyBarChart = ({barList}) => {
  const choosedBarList = barList && barList.length > 0 ? barList : defaultBarList
  return (
    <BarChart
      width={770}
      height={300}
      data={choosedBarList}
    >
      {/* <CartesianGrid strokeDasharray="1 1" /> */}
      <XAxis
        dataKey="type"
        tick={{ fill: '#d5c9c9', fontSize: 14 }}
      />
      <YAxis
        tick={{ fill: '#d5c9c9', fontSize: 14 }}
      />
      <Tooltip />
      {/* <Legend /> */}
      <Bar dataKey="pint">
        {
          choosedBarList.map((entry, index) => (
            <Cell 
              key={`cell-${index}`} 
              fill={entry.pint > 25 ? "rgb(25, 160, 25)" 
                : entry.pint > 10 ? "#ff9510"
                : "#8B0000"} 
            />
          ))
        }
      </Bar>
    </BarChart>
  );
};

export default MyBarChart;
