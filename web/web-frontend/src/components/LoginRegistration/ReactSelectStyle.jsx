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
        color: 'rgb(160, 25, 25)' 
        
    }),
    option: (provided) => ({
        ...provided,
        color:'rgb(160, 25, 25)',
        backgroundColor: 'transparent',
        padding:'10px 0px 10px 20px',
        '&:hover':{
            backgroundColor: '#d5c9c9',
            color: 'rgb(160, 25, 25);'
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
        backdropFilter: 'blur(15px)',
        boxShadow: '0px 0px 50px rgba(119, 81, 81, 0.6)',
        borderRadius: '20px'  
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
        color: '#d5c9c9' 
      }),
};
  

export default customStyles;
