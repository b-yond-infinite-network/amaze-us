import Users from "./modules/Users/components/UsersContainer";
import UserMoreInfo from "./modules/Users/components/UserMoreInfo";

const ApplicationRoutes = {
  Routes: [
    {
      path: "/users",
      exact: true,
      component: Users,
      key: "users",
    },
    {
      path: "/users/:id",
      exact: true,
      component: UserMoreInfo,
      key: "userMoreInfo",
    },
  ],
};

export default ApplicationRoutes;
