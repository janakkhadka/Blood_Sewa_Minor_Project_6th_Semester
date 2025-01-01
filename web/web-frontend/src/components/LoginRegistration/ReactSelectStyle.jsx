const customStyles = ({
  hoverBorderColor = 'rgba(255,255,255,.1)',
  focusBorderColor = 'rgba(255,255,255,.1)',
  defaultBorderColor = 'rgba(255,255,255,.1)',
  textColor = 'rgb(160, 25, 25)',
  backgroundColor = 'transparent',
  optionTextColor = '#d5c9c9',
  optionHoverBackgroundColor = '#d5c9c9',
  optionHoverTextColor = 'rgb(160, 25, 25)',
  singleValueTextColor = '#d5c9c9',
  menuBackground = 'transparent',
  placeholderColor = '#d5c9c9',
} = {}) => ({
  control: (provided, state) => ({
      ...provided,
      '&:hover': {
          border: `1px solid ${hoverBorderColor}`,
      },
      width: '100%',
      outline: 'none',
      backgroundColor,
      border: state.isFocused
          ? `1px solid ${focusBorderColor}`
          : `1px solid ${defaultBorderColor}`,
      boxShadow: state.isFocused ? 'none' : 'none',
      borderRadius: '40px',
      fontSize: '16px',
      padding: '7px 45px 5px 10px',
      color: textColor,
  }),
  option: (provided) => ({
      ...provided,
      color: optionTextColor,
      backgroundColor: 'transparent',
      padding: '10px 0px 10px 20px',
      '&:hover': {
          backgroundColor: optionHoverBackgroundColor,
          color: optionHoverTextColor,
      },
  }),
  singleValue: (provided) => ({
      ...provided,
      color: singleValueTextColor,
  }),
  menu: (provided) => ({
      ...provided,
      position: 'absolute',
      background: menuBackground,
      backdropFilter: 'blur(15px)',
      boxShadow: '0px 0px 50px rgba(119, 81, 81, 0.6)',
      borderRadius: '20px',
  }),
  menuList: (provided) => ({
    ...provided,
    scrollbarWidth: 'none', 
    msOverflowStyle: 'none', 
    '&::-webkit-scrollbar': {
      display: 'none',
    },
  }),

  
  indicatorSeparator: () => ({
    display: 'none',
  }),
  dropdownIndicator: (provided) => ({
    ...provided,
    display: 'none'
  }),
  placeholder: (provided) => ({
      ...provided,
      color: placeholderColor,
  }),
});

export default customStyles;
