const RolePermission = require('../models').RolePermission;
const Permission = require('../models').Permission;

class Helper {
    constructor() {}

    checkPermission(roleId, permName) {
   
        return new Promise(
            (resolve, reject) => {
                Permission.findOne({
                    where: {
                        perm_name: permName
                    }
                }).then((perm) => {
                   
                    RolePermission.findOne({
                        where: {
                            role_id: roleId,
                            perm_id: perm.id
                        }
                    }).then((rolePermission) => {
                        if(rolePermission) {
                            resolve(rolePermission);
                        } else {
                            reject({message: 'Forbidden'});
                        }
                    }).catch((error) => {
                        reject(error);
                    });
                }).catch(() => {
                    reject({message: 'Forbidden'});
                });
            }
        );
    }

    sendEmail(to, from="noreply@test.com",data=[]) {
   
        return new Promise(
            (resolve) => {
                resolve(true);
            }
        );
    }

    rand(min, max) {
        return Math.floor(Math.random() * (max - min + 1)) + min;
      }

    randomDate(start, end, startHour, endHour) {
        var date = new Date(+start +(Math.random() * (end - start)) );
        // var hour = startHour + Math.random() * (endHour - startHour) | 0;
        var hour = startHour + this.rand(startHour,endHour) | 0;
        date.setHours(hour);
        return date;
      }
}


module.exports = Helper;