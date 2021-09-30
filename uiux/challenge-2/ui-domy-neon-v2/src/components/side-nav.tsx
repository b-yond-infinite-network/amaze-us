import React from "react";
import { Menu } from "antd";
import { MenuItem } from "./types";
import { Link } from "react-router-dom";

interface Props {
  selectedItemKey?: string;
  items: MenuItem[];
}

const { SubMenu } = Menu;

const renderMenuItemReccursive = (item: MenuItem) : React.ReactNode => {
  if (!item.children) {
    return (
      <Menu.Item key={item.key} icon={item.icon} title={item.title}>
        <Link to={item.link!}>
          {item.title}
        </Link>
      </Menu.Item>
    )
  }

  return (
    <SubMenu key={item.key} title={item.title} icon={item.icon}>
      <>
        {item.children.map(child => renderMenuItemReccursive(child))}
      </>
    </SubMenu>
  );
}

const SideNav: React.FC<Props> = (props: Props) => {
  return (
    <Menu theme="dark" defaultSelectedKeys={props.selectedItemKey ? [props.selectedItemKey] : []} mode="inline">
      {props.items.map(item => renderMenuItemReccursive(item))}
    </Menu>
  );
};

export default SideNav;