export interface MenuItem {
  title: string;
  key: string;
  link?: string;
  icon?: React.ReactNode;
  children?: MenuItem[];
}
