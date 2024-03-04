export function userInfo(data=[]){
    return data.map(value=>({
        idUser: value.idUser,
        username: value.username,
        name: value.name,
        lastName: value.lastName,
        phone: value.phone,
        userType: value.userType
    }))
  }