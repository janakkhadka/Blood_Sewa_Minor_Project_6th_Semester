export const userAuthToken = localStorage.getItem('userAuthToken') || sessionStorage.getItem('userAuthToken');
export const orgAuthToken = localStorage.getItem('orgAuthToken') || sessionStorage.getItem('orgAuthToken');
console.log('org:'+orgAuthToken);
console.log('user:'+userAuthToken);