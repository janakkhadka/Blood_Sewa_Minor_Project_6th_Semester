const customStyles = {
    control: (provided,state) => ({
        ...provided,
        '&:hover':{
            border: '1px solid rgba(255,255,255,.1)',
        },
        width: '100%',
        outline: 'none',
        backgroundColor: 'transparent',
        border: state.isFocused
        ? '1px solid rgba(255,255,255,.1)':'1px solid rgba(255,255,255,.1)',
        boxShadow: state.isFocused ? 'none' : 'none',
        borderRadius: '40px',
        fontSize: '16px',
        padding: '7px 45px 5px 10px',
        color: '#d5c9c9' 
        
    }),
    option: (provided) => ({
        ...provided,
        backgroundColor: 'transparent',
        padding:'10px 0px 10px 20px',
        '&:hover':{
            backgroundColor: '#d5c9c9',
            color: '#1e90ff;'
        }, 

    }),
    singleValue: (provided) => ({
        ...provided,
        color: '#d5c9c9',
         
      }),
    menu: (provided) => ({
        ...provided,
        position:'absolute',
        background: 'transparent',
        backdropFilter: 'blur(5px)',
        boxShadow: '0px 0px 50px rgba(119, 81, 81, 0.6)',
        borderRadius: '20px'  
      }), 


    menuList: (provided) => ({
        ...provided,
        scrollbarWidth: 'none', // Firefox
        msOverflowStyle: 'none', // Internet Explorer
        '&::-webkit-scrollbar': {
          display: 'none', // Chrome, Safari, Opera
        },
    }),

      
    indicatorSeparator: () => ({
      display: 'none', // Hides the separator between the arrow and the select box
    }),
    dropdownIndicator: (provided) => ({
      ...provided,
      display: 'none'
    }),
    placeholder: (provided) => ({
        ...provided,
        color: '#d5c9c9'  // Change placeholder color to gray (or any color you like)
      }),
};
  

export default customStyles;
