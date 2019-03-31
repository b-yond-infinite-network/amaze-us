export default interface IRepository<T> {
    getAll(): Promise<T[]>;

    add(entity: T): Promise<T>;
}
