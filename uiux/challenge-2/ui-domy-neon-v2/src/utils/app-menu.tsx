import { MenuItem } from "../components/types";

import {
  TeamOutlined,
  UserOutlined,
  UserAddOutlined,
  RocketOutlined,
  BgColorsOutlined,
  ShoppingCartOutlined,
  ShopOutlined,
  PartitionOutlined,
  FilterOutlined,
} from '@ant-design/icons';

import { APP_URLS } from './constants';

export const buildAppNav = () : MenuItem[] => ([
  // {
  //   key: 'dashboard',
  //   title: 'Dashboard',
  //   icon: <PieChartOutlined />,
  //   link: APP_URLS.DASHBOARD
  // },
  {
    key: 'crew',
    title: 'Crew',
    icon: <TeamOutlined />,
    children: [{
      key: 'crew-members',
      title: 'Crew members',
      link: APP_URLS.CREW_MEMBERS,
      icon: <UserOutlined />,
    },
    {
      key: 'crew-birth-requests',
      title: 'Birth Requests',
      link: APP_URLS.BIRTH_REQUESTS,
      icon: <UserAddOutlined />,
    }]
  },
  {
    key: 'life-support',
    title: 'Life Support',
    icon: <RocketOutlined />,
    children: [{
      key: 'water-system',
      title: 'Water System',
      icon: <BgColorsOutlined />,
      link: APP_URLS.WATER_SYSTEM,
    }]
  },
  {
    key: 'food-management',
    title: 'Food Management',
    icon: <ShopOutlined />,
    children: [{
      key: 'food-levels',
      title: 'Food Levels',
      icon: <ShoppingCartOutlined />,
      link: APP_URLS.FOOD_LEVELS,
    },
    {
      key: 'plants',
      title: 'Plants',
      icon: <FilterOutlined />,
      link: APP_URLS.FOOD_PLANTS,
    },
    {
      key: 'seeds',
      title: 'Seeds',
      icon: <PartitionOutlined />,
      link: APP_URLS.FOOD_SEEDS,
    }]
  }
]);

