import React from 'react'

const Show = ({isVisible,children}) => {
  return isVisible? children: null
}

export default Show